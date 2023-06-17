package de.turnertech.ows.servlet;

import java.io.IOException;
import java.util.Map.Entry;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.ExceptionCode;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.OwsRequestContext;
import de.turnertech.ows.common.RequestHandler;
import de.turnertech.ows.gml.BoundingBox;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.parameter.WfsVersionValue;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemFormat;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsGetCapabilitiesRequest implements RequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext, OwsRequestContext requestContext) throws ServletException, IOException {
        response.setContentType(RequestHandler.CONTENT_XML);
        XMLStreamWriter out = null;
        WfsVersionValue requestedVersion = WfsVersionValue.V2_0_2;
        if(requestContext.getOwsVersion() != null) {
            requestedVersion = requestContext.getOwsVersion();
        }
        SpatialReferenceSystemFormat srsFormat = requestedVersion == WfsVersionValue.V2_0_0 ? SpatialReferenceSystemFormat.URN : SpatialReferenceSystemFormat.URI;

        try {
            out = XMLOutputFactory.newInstance().createXMLStreamWriter(response.getOutputStream(), "UTF-8");
            out.writeStartDocument("UTF-8", "1.0");
            out.writeStartElement("WFS_Capabilities");
            out.writeAttribute("service", "WFS");
            out.writeAttribute("version", requestedVersion.toString());
            out.writeDefaultNamespace(OwsContext.WFS_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.XSI_URI), OwsContext.XSI_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.GML_URI), OwsContext.GML_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.FES_URI), OwsContext.FES_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.OWS_URI), OwsContext.OWS_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.XSD_URI), OwsContext.XSD_URI);
            out.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", "http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0/wfs.xsd http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsAll.xsd http://www.opengis.net/fes/2.0 http://schemas.opengis.net/filter/2.0/filterAll.xsd");

            out.writeStartElement(OwsContext.OWS_URI, "ServiceIdentification");
                out.writeStartElement(OwsContext.OWS_URI, "Title");
                    out.writeCharacters(owsContext.getWfsCapabilities().getServiceTitle());
                out.writeEndElement();
                out.writeStartElement(OwsContext.OWS_URI, "Abstract");
                    out.writeCharacters(owsContext.getWfsCapabilities().getServiceAbstract());
                out.writeEndElement();
                out.writeStartElement(OwsContext.OWS_URI, "Keywords");
                    for(String keyword : owsContext.getWfsCapabilities().getKeywords()) {
                        out.writeStartElement(OwsContext.OWS_URI, "Keyword");
                            out.writeCharacters(keyword);
                        out.writeEndElement();
                    }
                    out.writeStartElement(OwsContext.OWS_URI, "Type");
                        out.writeCharacters("String");
                    out.writeEndElement();
                out.writeEndElement();
                out.writeStartElement(OwsContext.OWS_URI, "ServiceType");
                    out.writeCharacters(owsContext.getWfsCapabilities().getServiceType().toString());
                out.writeEndElement();
                for(WfsVersionValue serviceTypeVersion : owsContext.getWfsCapabilities().getServiceTypeVersions()) {
                    out.writeStartElement(OwsContext.OWS_URI, "ServiceTypeVersion");
                        out.writeCharacters(serviceTypeVersion.toString());
                    out.writeEndElement();
                }
                out.writeStartElement(OwsContext.OWS_URI, "Fees");
                    out.writeCharacters(owsContext.getWfsCapabilities().getFees());
                out.writeEndElement();
                out.writeStartElement(OwsContext.OWS_URI, "AccessConstraints");
                    out.writeCharacters(owsContext.getWfsCapabilities().getAccessConstraints());
                out.writeEndElement();
            out.writeEndElement();

            out.writeStartElement(OwsContext.OWS_URI, "OperationsMetadata");
                out.writeStartElement(OwsContext.OWS_URI, "Operation");
                out.writeAttribute("name", "GetCapabilities");
                    out.writeStartElement(OwsContext.OWS_URI, "Parameter");
                    out.writeAttribute("name", "AcceptVersions");
                        out.writeStartElement(OwsContext.OWS_URI, "AllowedValues");
                            for(WfsVersionValue allowedVersion : owsContext.getWfsCapabilities().getServiceTypeVersions()) {
                                out.writeStartElement(OwsContext.OWS_URI, "Value");
                                    out.writeCharacters(allowedVersion.toString());
                                out.writeEndElement();
                            }
                        out.writeEndElement();
                    out.writeEndElement();
                out.writeEndElement();
            out.writeEndElement();

            /**
            <ows:OperationsMetadata>
            <ows:Operation name="GetCapabilities">
               <ows:DCP>
                  <ows:HTTP>
                     <ows:Get xlink:href="http://www.BlueOx.org/wfs/wfs.cgi?"/>
                     <ows:Post xlink:href="http://www.BlueOx.org/wfs/wfs.cgi"/>
                  </ows:HTTP>
               </ows:DCP>
               <ows:Parameter name="AcceptVersions">
                  <ows:AllowedValues>
                  <ows:Value>2.0.2</ows:Value>
                  <ows:Value>2.0.0</ows:Value>
                  </ows:AllowedValues>
               </ows:Parameter>
            </ows:Operation> */

            out.writeStartElement(OwsContext.WFS_URI, "FeatureTypeList");
            for(FeatureType featureType : owsContext.getWfsCapabilities().getFeatureTypes()) {
                out.writeStartElement(OwsContext.WFS_URI, "FeatureType");
                    out.writeStartElement(OwsContext.WFS_URI, "Name");
                        out.writeCharacters(owsContext.getXmlNamespacePrefix(featureType.getNamespace()) + ":" + featureType.getName());
                    out.writeEndElement();
                    if(featureType.getTitle() != null) {
                        out.writeStartElement(OwsContext.WFS_URI, "Title");
                            out.writeCharacters(featureType.getTitle());
                        out.writeEndElement();
                    }
                    if(featureType.getSrs() == null) {
                        out.writeEmptyElement(OwsContext.WFS_URI, "NoCRS");
                    } else {
                        out.writeStartElement(OwsContext.WFS_URI, "DefaultCRS");
                            out.writeCharacters(new SpatialReferenceSystemRepresentation(featureType.getSrs(), srsFormat).toString());
                        out.writeEndElement();
                        for(SpatialReferenceSystem srs : SpatialReferenceSystem.values()) {
                            if(srs != featureType.getSrs()) {
                                out.writeStartElement(OwsContext.WFS_URI, "OtherCRS");
                                    out.writeCharacters(new SpatialReferenceSystemRepresentation(srs, srsFormat).toString());
                                out.writeEndElement();
                            }
                        }
                    }

                    BoundingBox boundingBox = owsContext.getModelProvider().getModel(featureType).getBoundingBox();
                    if(boundingBox != null) {
                        out.writeStartElement(OwsContext.OWS_URI, "WGS84BoundingBox");
                            out.writeStartElement(OwsContext.OWS_URI, "LowerCorner");
                                out.writeCharacters(Double.toString(boundingBox.getWest()) + " " + Double.toString(boundingBox.getSouth()));
                            out.writeEndElement();
                            out.writeStartElement(OwsContext.OWS_URI, "UpperCorner");
                                out.writeCharacters(Double.toString(boundingBox.getEast()) + " " + Double.toString(boundingBox.getNorth()));
                            out.writeEndElement();
                        out.writeEndElement();
                    }

                out.writeEndElement();
            }
            out.writeEndElement();

            out.writeStartElement(OwsContext.FES_URI, "Filter_Capabilities");
                out.writeStartElement(OwsContext.FES_URI, "Conformance");
                    for(Entry<String, Boolean> entry : owsContext.getWfsCapabilities().getFilterConformance().entrySet()) {
                        out.writeStartElement(OwsContext.FES_URI, "Constraint");
                            out.writeAttribute(OwsContext.WFS_URI, "name", entry.getKey());
                            out.writeEmptyElement(OwsContext.OWS_URI, "NoValues");
                            out.writeStartElement(OwsContext.OWS_URI, "Constraint");
                                out.writeCharacters(entry.getValue().toString().toUpperCase());
                            out.writeEndElement();
                        out.writeEndElement();
                    }
                out.writeEndElement();
            out.writeEndElement();

            out.writeEndElement();
            out.writeEndDocument();
        } catch (Exception e) {
            response.sendError(500, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PROCESSING_FAILED.toString(), "GetCapabilities", "XML Constrction of the response failed."));
        }
    }
}
