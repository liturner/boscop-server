package de.turnertech.ows.servlet;

import java.io.IOException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.ExceptionCode;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.OwsRequestContext;
import de.turnertech.ows.common.RequestHandler;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.FeatureType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsDescribeFeatureTypeRequest implements RequestHandler {
    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext, OwsRequestContext requestContext) throws ServletException, IOException {
        String targetNamespace = null;
        for(FeatureType featureType : owsContext.getWfsCapabilities().getFeatureTypes()) {
            if(targetNamespace == null) {
                targetNamespace = featureType.getNamespace();
            } else if(!targetNamespace.equals(featureType.getNamespace())) {
                response.sendError(500, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PROCESSING_FAILED.toString(), "DescribeFeatureType", "Multiple namespaces found during operation, but this server does not support multiple FeatureType namespaces!"));
                return;
            }
        }
        
        response.setContentType(RequestHandler.CONTENT_XML);
        XMLStreamWriter out = null;

        try {
            out = XMLOutputFactory.newInstance().createXMLStreamWriter(response.getOutputStream(), "UTF-8");
            out.writeStartDocument("UTF-8", "1.0");
            out.writeStartElement(owsContext.getXmlNamespacePrefix(OwsContext.XSD_URI), "schema", OwsContext.XSD_URI);
            out.writeAttribute("targetNamespace", targetNamespace);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.GML_URI), OwsContext.GML_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.XSD_URI), OwsContext.XSD_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.XSI_URI), OwsContext.XSI_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(targetNamespace), targetNamespace);

                out.writeEmptyElement(OwsContext.XSD_URI, "import");
                out.writeAttribute("target", OwsContext.GML_URI);
                out.writeAttribute("schemaLocation", owsContext.getXmlNamespaceSchema(OwsContext.GML_URI));

                for(FeatureType featureType : owsContext.getWfsCapabilities().getFeatureTypes()) {
                    out.writeStartElement(OwsContext.XSD_URI, "complexType");
                    out.writeAttribute("name", featureType.getName() + "Type");
                        out.writeStartElement(OwsContext.XSD_URI, "complexContent");
                            out.writeStartElement(OwsContext.XSD_URI, "extension");
                            out.writeAttribute("base", "gml:AbstractFeatureType");
                                out.writeStartElement(OwsContext.XSD_URI, "sequence");
                                for(FeatureProperty property : featureType.getProperties()) {
                                    if(property.equals(featureType.getIdProperty())) {
                                        continue;
                                    }
                                    out.writeEmptyElement(OwsContext.XSD_URI, "element");
                                    out.writeAttribute("minOccurs", Integer.toString(property.getMinOccurs()));
                                    out.writeAttribute("maxOccurs", Integer.toString(property.getMaxOccurs()));
                                    out.writeAttribute("name", property.getName());
                                    out.writeAttribute("type", owsContext.getXmlNamespacePrefix(property.getPropertyType().getQualifiedName().getNamespaceURI()) + ":" + property.getPropertyType().getQualifiedName().getLocalPart());
                                }
                                out.writeEndElement();
                            out.writeEndElement();
                        out.writeEndElement();
                    out.writeEndElement();
                    out.writeEmptyElement(OwsContext.XSD_URI, "element");
                    out.writeAttribute("name", featureType.getName());
                    out.writeAttribute("type", owsContext.getXmlNamespacePrefix(featureType.getNamespace()) + ":" + featureType.getName() + "Type");
                    out.writeAttribute("substitutionGroup", owsContext.getXmlNamespacePrefix(OwsContext.GML_URI) + ":AbstractFeature");
                }

            out.writeEndElement();
            out.writeEndDocument();
        } catch (Exception e) {
            response.sendError(500, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PROCESSING_FAILED.toString(), "DescribeFeatureType", "XML Construction of the response failed."));
        }
    }
}
