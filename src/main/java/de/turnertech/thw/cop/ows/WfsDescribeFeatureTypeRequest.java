package de.turnertech.thw.cop.ows;

import java.io.IOException;
import java.io.PrintWriter;

import de.turnertech.thw.cop.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsDescribeFeatureTypeRequest {
    
    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(Constants.ContentTypes.XML);
        PrintWriter writer = response.getWriter();
        writer.write(
            """
            <?xml version="1.0" ?>
            <xsd:schema targetNamespace="urn:ns:de:turnertech:boscop" xmlns:boscop="urn:ns:de:turnertech:boscop" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns="http://www.w3.org/2001/XMLSchema" xmlns:gml="http://www.opengis.net/gml/3.2" elementFormDefault="qualified" version="2.0.2">
                <xsd:import namespace="http://www.opengis.net/gml/3.2" schemaLocation="http://schemas.opengis.net/gml/3.2.1/gml.xsd"/>
                <!-- ============================================= define global elements  ============================================= -->
                <xsd:element name="Unit" type="boscop:UnitType" substitutionGroup="gml:AbstractFeature"/>
                <xsd:element name="Area" type="boscop:AreaType" substitutionGroup="gml:AbstractFeature"/>
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
            </xsd:schema>
            """
        );
    }
}
