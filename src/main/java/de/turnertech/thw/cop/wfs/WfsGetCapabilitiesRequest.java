package de.turnertech.thw.cop.wfs;

import java.io.IOException;
import java.io.PrintWriter;

import de.turnertech.thw.cop.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsGetCapabilitiesRequest {

    private WfsGetCapabilitiesRequest() {
        
    }

    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(Constants.ContentTypes.XML);
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
                    <DefaultCRS>urn:ogc:def:crs:EPSG::4326</DefaultCRS>
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
