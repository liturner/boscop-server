package de.turnertech.ows.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsGetCapabilitiesRequest implements RequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext, OwsRequestContext requestContext) throws ServletException, IOException {
        response.setContentType(RequestHandler.CONTENT_XML);
        XMLStreamWriter out = null;
        try {
            out = XMLOutputFactory.newInstance().createXMLStreamWriter(response.getOutputStream(), "UTF-8");
            out.writeStartDocument("UTF-8", "1.0");
            out.writeStartElement("WFS_Capabilities");
            out.writeAttribute("service", "WFS");
            out.writeAttribute("version", WfsVersionValue.V2_0_2.toString());
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
                            out.writeCharacters(featureType.getSrs().getUri());
                        out.writeEndElement();
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

    @Deprecated
    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(RequestHandler.CONTENT_XML);
        PrintWriter writer = response.getWriter();
        writer.write("""
        <?xml version="1.0"?>
        <WFS_Capabilities version="2.0.2" xmlns="http://www.opengis.net/wfs/2.0" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:fes="http://www.opengis.net/fes/2.0" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:ows="http://www.opengis.net/ows/1.1" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0/wfs.xsd http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsAll.xsd">
        <ows:ServiceIdentification>
            <ows:Title>BOSCOP WFS</ows:Title>
            <ows:Abstract>WFS view on the BOSCOP Common Operational Picture</ows:Abstract>
            <ows:Keywords>
                <ows:Keyword>BOS</ows:Keyword>
                <ows:Keyword>COP</ows:Keyword>
                <ows:Keyword>BBK</ows:Keyword>
                <ows:Keyword>THW</ows:Keyword>
                <ows:Type>String</ows:Type>
            </ows:Keywords>
            <ows:ServiceType>WFS</ows:ServiceType>
            <ows:ServiceTypeVersion>2.0.2</ows:ServiceTypeVersion>
            <ows:ServiceTypeVersion>2.0.0</ows:ServiceTypeVersion>
            <ows:Fees>Due as per BOSCOP License Agreement</ows:Fees>
            <ows:AccessConstraints>As per BOSCOP License Agreement</ows:AccessConstraints>
            </ows:ServiceIdentification>
            <ows:ServiceProvider>
                <ows:ProviderName>BlueOx Inc.</ows:ProviderName>
                <ows:ProviderSite xlink:href="http://www.cubewerx.com"/>
                <ows:ServiceContact>
                    <ows:IndividualName>Paul Bunyon</ows:IndividualName>
                    <ows:PositionName>Mythology Manager</ows:PositionName>
                    <ows:ContactInfo>
                        <ows:Phone>
                            <ows:Voice>1.800.BIG.WOOD</ows:Voice>
                            <ows:Facsimile>1.800.FAX.WOOD</ows:Facsimile>
                    </ows:Phone>
                    <ows:Address>
                        <ows:DeliveryPoint>North Country</ows:DeliveryPoint>
                        <ows:City>Small Town</ows:City>
                        <ows:AdministrativeArea>Rural County</ows:AdministrativeArea>
                        <ows:PostalCode>12345</ows:PostalCode>
                        <ows:Country>USA</ows:Country>
                        <ows:ElectronicMailAddress>Paul.Bunyon@BlueOx.org</ows:ElectronicMailAddress>
                    </ows:Address>
                    <ows:OnlineResource xlink:href="http://www.BlueOx.org/contactUs"/>
                        <ows:HoursOfService>24x7</ows:HoursOfService>
                        <ows:ContactInstructions>Todo</ows:ContactInstructions>
                    </ows:ContactInfo>
                    <ows:Role>PointOfContact</ows:Role>
                </ows:ServiceContact>
            </ows:ServiceProvider>
            <ows:OperationsMetadata>
                <ows:Operation name="GetCapabilities">
                    <ows:DCP>
                        <ows:HTTP>
        """);
        writer.write("<ows:Get xlink:href=\"" + request.getRequestURL().toString() + "?\"/>");
        writer.write("""
                        </ows:HTTP>
                    </ows:DCP>
                    <ows:Parameter name="AcceptVersions">
                        <ows:AllowedValues>
                            <ows:Value>2.0.2</ows:Value>
                            <ows:Value>2.0.0</ows:Value>
                        </ows:AllowedValues>
                    </ows:Parameter>
                </ows:Operation>
                <ows:Operation name="DescribeFeatureType">
                    <ows:DCP>
                        <ows:HTTP>
        """);
        writer.write("<ows:Get xlink:href=\"" + request.getRequestURL().toString() + "?\"/>");
        writer.write("""
                        </ows:HTTP>
                    </ows:DCP>
                </ows:Operation>
                <ows:Operation name="ListStoredQueries">
                    <ows:DCP>
                        <ows:HTTP>
        """);
        writer.write("<ows:Get xlink:href=\"" + request.getRequestURL().toString() + "?\"/>");
        writer.write("""
                        </ows:HTTP>
                    </ows:DCP>
                </ows:Operation>
                <ows:Operation name="DescribeStoredQueries">
                    <ows:DCP>
                        <ows:HTTP>
        """);
        writer.write("<ows:Get xlink:href=\"" + request.getRequestURL().toString() + "?\"/>");
        writer.write("""
                        </ows:HTTP>
                    </ows:DCP>
                </ows:Operation>
                <ows:Operation name="GetFeature">
                    <ows:DCP>
                        <ows:HTTP>
        """);
        writer.write("<ows:Get xlink:href=\"" + request.getRequestURL().toString() + "?\"/>");
        writer.write("""
                        </ows:HTTP>
                    </ows:DCP>
                </ows:Operation>
                <ows:Parameter name="version">
                    <ows:AllowedValues>
                        <ows:Value>2.0.2</ows:Value>
                        <ows:Value>2.0.0</ows:Value>
                    </ows:AllowedValues>
                </ows:Parameter>
                <!-- ***************************************************** -->
                <!-- * CONFORMANCE DECLARATION * -->
                <!-- ***************************************************** -->
                <ows:Constraint name="ImplementsBasicWFS">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ImplementsTransactionalWFS">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ImplementsLockingWFS">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                    <ows:Constraint name="KVPEncoding">
                    <ows:NoValues/>
                    <ows:DefaultValue>TRUE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="XMLEncoding">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="SOAPEncoding">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ImplementsInheritance">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ImplementsRemoteResolve">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ImplementsResultPaging">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ImplementsStandardJoins">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ImplementsSpatialJoins">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ImplementsTemporalJoins">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ImplementsFeatureVersioning">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="ManageStoredQueries">
                    <ows:NoValues/>
                    <ows:DefaultValue>FALSE</ows:DefaultValue>
                </ows:Constraint>
                <!-- ***************************************************** -->
                <!-- * CAPACITY CONSTRAINTS * -->
                <!-- ***************************************************** -->
                <ows:Constraint name="CountDefault">
                    <ows:NoValues/>
                    <ows:DefaultValue>1000</ows:DefaultValue>
                </ows:Constraint>
                <ows:Constraint name="QueryExpressions">
                    <ows:AllowedValues>
                    <ows:Value>wfs:StoredQuery</ows:Value>
                </ows:AllowedValues>
                </ows:Constraint>
                <!-- ***************************************************** -->
            </ows:OperationsMetadata>
            <FeatureTypeList>
                <FeatureType xmlns:boscop="urn:ns:de:turnertech:boscop">
                    <Name>boscop:Unit</Name>
                    <Title>Unit</Title>
                    <Abstract>A Unit, such as a Group or Company</Abstract>
                    <ows:Keywords>
                        <ows:Keyword>group</ows:Keyword>
                        <ows:Keyword>unit</ows:Keyword>
                    </ows:Keywords>
                    <DefaultCRS>http://www.opengis.net/def/crs/EPSG/0/4326</DefaultCRS>
                    <ows:WGS84BoundingBox>
                        <ows:LowerCorner>-180 -90</ows:LowerCorner>
                        <ows:UpperCorner>180 90</ows:UpperCorner>
                    </ows:WGS84BoundingBox>
                </FeatureType>
                <FeatureType xmlns:boscop="urn:ns:de:turnertech:boscop">
                    <Name>boscop:Area</Name>
                    <Title>Area</Title>
                    <Abstract>An Area, such as a flooded zone or fire</Abstract>
                    <ows:Keywords>
                        <ows:Keyword>group</ows:Keyword>
                        <ows:Keyword>area</ows:Keyword>
                    </ows:Keywords>
                    <DefaultCRS>http://www.opengis.net/def/crs/EPSG/0/4326</DefaultCRS>
                    <ows:WGS84BoundingBox>
                        <ows:LowerCorner>-180 -90</ows:LowerCorner>
                        <ows:UpperCorner>180 90</ows:UpperCorner>
                    </ows:WGS84BoundingBox>
                </FeatureType>
            </FeatureTypeList>
            <fes:Filter_Capabilities>
                <fes:Conformance>
                    <fes:Constraint name="ImplementsQuery">
                        <ows:NoValues/>
                        <ows:DefaultValue>TRUE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsAdHocQuery">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsFunctions">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsMinStandardFilter">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsStandardFilter">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsMinSpatialFilter">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsSpatialFilter">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsMinTemporalFilter">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsTemporalFilter">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsVersionNav">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsSorting">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                    <fes:Constraint name="ImplementsExtendedOperators">
                        <ows:NoValues/>
                        <ows:DefaultValue>FALSE</ows:DefaultValue>
                    </fes:Constraint>
                </fes:Conformance>
            </fes:Filter_Capabilities>
        </WFS_Capabilities>
        """);
    }
}