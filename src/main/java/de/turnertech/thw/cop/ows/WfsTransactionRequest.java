package de.turnertech.thw.cop.ows;

import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.ErrorServlet;
import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.model.area.AreaDecoder;
import de.turnertech.thw.cop.model.area.AreaModel;
import de.turnertech.thw.cop.model.hazard.HazardDecoder;
import de.turnertech.thw.cop.model.hazard.HazardModel;
import de.turnertech.thw.cop.ows.api.Model;
import de.turnertech.thw.cop.ows.api.OwsContext;
import de.turnertech.thw.cop.ows.api.OwsRequestContext;
import de.turnertech.thw.cop.ows.filter.OgcFilter;
import de.turnertech.thw.cop.ows.filter.OgcFilterDecoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsTransactionRequest implements RequestHandler  {
    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext, OwsRequestContext requestContext) throws ServletException, IOException {       
        response.setContentType(Constants.ContentTypes.XML);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(request.getInputStream());
            Element root = document.getDocumentElement();

            NodeList deleteEntries = root.getElementsByTagName("Delete");
            NodeList insertEntries = root.getElementsByTagName("Insert");

            for(int i = 0; i < deleteEntries.getLength(); ++i) {
                Logging.LOG.info("Transaction - Delete");

                // Delete entries have one child element, and one attribute. There is no need to cast to Element here
                // 15.3.4
                Node deleteEntry = deleteEntries.item(i);

                if(deleteEntry == null) {
                    Logging.LOG.severe("Something went wrong getting the Delete elements from a transaction");
                    response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "Delete", "Something went wrong decoding the Delete entry"));
                    return;
                }

                Node filter = deleteEntry.getFirstChild();
                if(filter == null) {
                    Logging.LOG.severe("Something went wrong getting the Filter element from a Delete Transaction");
                    response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "Filter", "Could not locate the Filter element for a Delete operation"));
                    return;
                }

                Node typeNameNode = deleteEntry.getAttributes().getNamedItem("typeName");
                if(typeNameNode == null) {
                    Logging.LOG.severe("Something went wrong getting the typeName attribute from a Filter");
                    response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "typeName", "Could not locate the typeName element for a Filter operation"));
                    return;
                }
                String typeName = typeNameNode.getNodeValue();

                OgcFilter ogcFilter = OgcFilterDecoder.getFilter(filter);


    
                if(HazardModel.TYPENAME.equals(typeName)) {
                    Collection<Feature> hazards = HazardModel.INSTANCE.filter(ogcFilter);
                    HazardModel.INSTANCE.removeAll(hazards);
                } else if (AreaModel.TYPENAME.equals(typeName)) {
                    Collection<Feature> areas = AreaModel.INSTANCE.filter(ogcFilter);
                    AreaModel.INSTANCE.removeAll(areas);
                }

            }

            for(int i = 0; i < insertEntries.getLength(); ++i) {
                Logging.LOG.info("Transaction - Insert");
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

                        Logging.LOG.severe(namespaceUri);
                        Logging.LOG.severe(name);
                        FeatureType featureType = owsContext.getWfsCapabilities().getFeatureType(namespaceUri, name);

                        if(featureType == null) {
                            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PARSING_FAILED.toString(), "Insert", "Feature Type not supported: " + name));
                            return;
                        }

                        Model model = owsContext.getModelProvider().getModel(featureType);
                        
                    }
                }

                AreaModel.INSTANCE.addAll(AreaDecoder.getAreas(root));
                HazardModel.INSTANCE.addAll(HazardDecoder.getHazards(root));
            }

            
        } catch (Exception e) {
            Logging.LOG.severe("Could not decode GML from Transaction");
        }
    }
}
