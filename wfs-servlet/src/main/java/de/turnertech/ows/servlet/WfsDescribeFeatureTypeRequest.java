package de.turnertech.ows.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.ExceptionCode;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.OwsRequestContext;
import de.turnertech.ows.common.RequestHandler;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.parameter.WfsVersionValue;
import de.turnertech.ows.srs.SpatialReferenceSystemFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsDescribeFeatureTypeRequest implements RequestHandler {
    
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
            out.writeStartElement(owsContext.getXmlNamespacePrefix(OwsContext.XSD_URI), "schema", OwsContext.XSD_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.GML_URI), OwsContext.GML_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.XSD_URI), OwsContext.XSD_URI);
            out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.XSI_URI), OwsContext.XSI_URI);
            out.writeNamespace("targetNamespace", "urn:ns:de:turnertech:boscop");

            Set<String> writtenNamespace = new HashSet<>(1);
            for(FeatureType featureType : owsContext.getWfsCapabilities().getFeatureTypes()) {
                if(!writtenNamespace.contains(featureType.getNamespace())) {
                    out.writeNamespace(owsContext.getXmlNamespacePrefix(featureType.getNamespace()), featureType.getNamespace());
                    writtenNamespace.add(featureType.getNamespace());
                }
            }

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
        
        
        /**
        PrintWriter writer = response.getWriter();
        writer.write(
            """
            <?xml version="1.0" ?>
            <xsd:schema targetNamespace="urn:ns:de:turnertech:boscop" xmlns:boscop="urn:ns:de:turnertech:boscop" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns="http://www.w3.org/2001/XMLSchema" xmlns:gml="http://www.opengis.net/gml/3.2" elementFormDefault="qualified" version="2.0.2">
                <xsd:import namespace="http://www.opengis.net/gml/3.2" schemaLocation="http://schemas.opengis.net/gml/3.2.1/gml.xsd"/>
                <!-- ============================================= define global elements  ============================================= -->
                <xsd:element name="Unit" type="boscop:UnitType" substitutionGroup="gml:AbstractFeature"/>
                <xsd:element name="Area" type="boscop:AreaType" substitutionGroup="gml:AbstractFeature"/>
                <xsd:element name="Hazard" type="boscop:HazardType" substitutionGroup="gml:AbstractFeature"/>
                <xsd:element name="Annotation" type="boscop:AnnotationType" substitutionGroup="gml:AbstractFeature"/>
                <!-- ============================================ define complex types (classes) ============================================ -->
                <xsd:complexType name="UnitType">
                    <xsd:complexContent>
                        <xsd:extension base="gml:AbstractFeatureType">
                            <xsd:sequence>
                                <xsd:element minOccurs="1" nillable="false" name="geometry" type="gml:PointPropertyType"/>
                                <xsd:element minOccurs="1" nillable="false" name="opta" type="xsd:string"/>
                            </xsd:sequence>
                        </xsd:extension>
                    </xsd:complexContent>
                </xsd:complexType>
                <xsd:complexType name="AreaType">
                    <xsd:complexContent>
                        <xsd:extension base="gml:AbstractFeatureType">
                            <xsd:sequence>
                                <xsd:element minOccurs="1" nillable="false" name="geometry" type="gml:SurfacePropertyType"/>
                                <xsd:element minOccurs="1" nillable="false" name="areaType" type="xsd:string"/>
                            </xsd:sequence>
                        </xsd:extension>
                    </xsd:complexContent>
                </xsd:complexType>
                <xsd:complexType name="HazardType">
                    <xsd:complexContent>
                        <xsd:extension base="gml:AbstractFeatureType">
                            <xsd:sequence>
                                <xsd:element minOccurs="1" nillable="false" name="geometry" type="gml:PointPropertyType"/>
                                <xsd:element minOccurs="1" nillable="false" name="hazardType" type="xsd:string"/>
                            </xsd:sequence>
                        </xsd:extension>
                    </xsd:complexContent>
                </xsd:complexType>
                <xsd:complexType name="AnnotationType">
                    <xsd:complexContent>
                        <xsd:extension base="gml:AbstractFeatureType">
                            <xsd:sequence>
                                <xsd:element minOccurs="1" nillable="false" name="geometry" type="gml:GeometryPropertyType"/>
                            </xsd:sequence>
                        </xsd:extension>
                    </xsd:complexContent>
                </xsd:complexType>
            </xsd:schema>
            """
        );
        
        */
    }
}
