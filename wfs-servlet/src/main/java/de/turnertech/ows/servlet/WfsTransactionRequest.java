package de.turnertech.ows.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.turnertech.ows.Logging;
import de.turnertech.ows.common.ExceptionCode;
import de.turnertech.ows.common.Model;
import de.turnertech.ows.common.ModelEncoder;
import de.turnertech.ows.common.ModelEncoderProvider;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.OwsRequestContext;
import de.turnertech.ows.common.RequestHandler;
import de.turnertech.ows.filter.OgcFilter;
import de.turnertech.ows.filter.OgcFilterDecoder;
import de.turnertech.ows.gml.FeatureDecoder;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.GmlDecoderContext;
import de.turnertech.ows.gml.IFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsTransactionRequest implements RequestHandler  {
    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext, OwsRequestContext requestContext) throws ServletException, IOException {       
        response.setContentType(RequestHandler.CONTENT_XML);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(request.getInputStream());

            Element root = document.getDocumentElement();
            root.normalize();

            NodeList deleteEntries = root.getElementsByTagName("Delete");
            NodeList insertEntries = root.getElementsByTagName("Insert");

            for(int i = 0; i < deleteEntries.getLength(); ++i) {
                Logging.LOG.fine("Transaction - Delete");

                // Delete entries have one child element, and one attribute. There is no need to cast to Element here
                // 15.3.4
                Node deleteEntry = deleteEntries.item(i);
                if(deleteEntry == null) {
                    Logging.LOG.severe("Something went wrong getting the Delete elements from a transaction");
                    response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "Delete", "Something went wrong decoding the Delete entry"));
                    return;
                }

                NodeList filterNodeList = ((Element)deleteEntry).getElementsByTagName("Filter");
                if(filterNodeList == null || filterNodeList.getLength() == 0) {
                    Logging.LOG.severe("Something went wrong getting the Filter element from a Delete Transaction");
                    response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "Filter", "Could not locate the Filter element for a Delete operation"));
                    return;
                }
                Node filter = filterNodeList.item(0);

                Node typeNameNode = deleteEntry.getAttributes().getNamedItem("typeName");
                if(typeNameNode == null) {
                    Logging.LOG.severe("Something went wrong getting the typeName attribute from a Filter");
                    response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "typeName", "Could not locate the typeName element for a Filter operation"));
                    return;
                }

                String prefix = null;
                String typeName = typeNameNode.getNodeValue();
                if(typeName.contains(":")) {
                    String[] parts = typeName.split(":", 2);
                    prefix = parts[0];
                    typeName = parts[1];
                }

                String namespaceUri = root.getAttribute("xmlns:" + prefix);
                if(namespaceUri == null || "".equals(namespaceUri)) {
                    Node nsElement = deleteEntry.getAttributes().getNamedItem("xmlns:" + prefix);
                    namespaceUri = nsElement == null ? null : nsElement.getTextContent();
                }
                if(namespaceUri == null || "".equals(namespaceUri)) {
                    Logging.LOG.severe("Something went wrong getting the typeName xmlns attribute from a Filter");
                    response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "typeName", "Could not locate the typeName xmlns element for a Filter operation"));
                    return;
                }

                OgcFilter ogcFilter = OgcFilterDecoder.getFilter(filter);
                FeatureType featureType = owsContext.getWfsCapabilities().getFeatureType(namespaceUri, typeName);
                Model model = owsContext.getModelProvider().getModel(featureType);
                Collection<IFeature> featuresToRemove = model.filter(ogcFilter);
                model.removeAll(featuresToRemove);
            }

            for(int i = 0; i < insertEntries.getLength(); ++i) {
                Logging.LOG.fine("Transaction - Insert");
                Element insertEntry = insertEntries.item(i) instanceof Element ? (Element)insertEntries.item(i) : null;

                if(insertEntry == null) {
                    Logging.LOG.severe("Something went wrong getting the Insert elements from a transaction");
                    continue;
                }

                NodeList individualFeatureEntries = insertEntry.getChildNodes();
                for(int j = 0; j < individualFeatureEntries.getLength(); ++j) {
                    Node featureEntry = individualFeatureEntries.item(j);
                    if(featureEntry.getNodeType() == Node.ELEMENT_NODE) {
                        String prefix = featureEntry.getPrefix();
                        String namespaceUri = featureEntry.getNamespaceURI();
                        String name = featureEntry.getNodeName();
                        if(namespaceUri == null && prefix == null && name.contains(":")) {
                            String[] parts = name.split(":", 2);
                            prefix = parts[0];
                            name = parts[1];
                        }
                        if(namespaceUri == null && prefix != null) {
                            namespaceUri = document.lookupNamespaceURI(prefix);
                        }
                        if(namespaceUri == null && prefix != null) {
                            namespaceUri = root.getAttribute("xmlns:" + prefix);
                            namespaceUri = "".equals(namespaceUri) ? null : namespaceUri;
                        }
                        if(namespaceUri == null) {
                            Node localXmlnsNode = featureEntry.getAttributes().getNamedItem("xmlns");
                            namespaceUri = localXmlnsNode == null ? null : localXmlnsNode.getTextContent();
                        }                        
                        if(namespaceUri == null) {
                            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "xmlns", "No xmlns supplied for: " + featureEntry.getNodeName()));
                            return;
                        }

                        FeatureType featureType = owsContext.getWfsCapabilities().getFeatureType(namespaceUri, name);

                        if(featureType == null) {
                            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "Insert", "Feature Type not supported: " + name));
                            return;
                        }

                        Model model = owsContext.getModelProvider().getModel(featureType);
                        IFeature feature = FeatureDecoder.decode(featureEntry, new GmlDecoderContext(), featureType);
                        model.add(feature);
                    }
                }
            }

            ModelEncoderProvider modelEncoderProvider = owsContext.getModelEncoderProvider();
            ModelEncoder encoder = modelEncoderProvider.getModelEncoder(requestContext, ModelEncoderProvider.GML32);

            
            
        } catch (Exception e) {
            Logging.LOG.severe("Could not decode GML from Transaction");
        }
    }
}
