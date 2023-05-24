package de.turnertech.thw.cop.gml;

public enum FeaturePropertyType {
    
    TEXT,
    INTEGER,
    DOUBLE,
    INSTANT,
    POLYGON,
    POLYGON_TYPE; // TODO: Explain that the difference is the element name, and that this references the xsd "gml:PolygonType", making this an "is a" Polygon, rather than a "has a" Polygon

}
