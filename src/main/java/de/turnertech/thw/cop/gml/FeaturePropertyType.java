package de.turnertech.thw.cop.gml;

public enum FeaturePropertyType {
    
    ID(false),
    TEXT(false),
    INTEGER(false),
    DOUBLE(false),
    INSTANT(false),
    POINT(true),
    POINT_TYPE(true),
    POLYGON(true),
    POLYGON_TYPE(true); // TODO: Explain that the difference is the element name, and that this references the xsd "gml:PolygonType", making this an "is a" Polygon, rather than a "has a" Polygon

    private final boolean isBoundingBoxProvider;

    private FeaturePropertyType(boolean isBoundingBoxProvider) {
        this.isBoundingBoxProvider = isBoundingBoxProvider;
    }

    public boolean isBoundingBoxProvider() {
        return this.isBoundingBoxProvider;
    }

}
