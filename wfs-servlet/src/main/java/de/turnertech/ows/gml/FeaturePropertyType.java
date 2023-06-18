package de.turnertech.ows.gml;

import javax.xml.namespace.QName;

import de.turnertech.ows.common.OwsContext;

public enum FeaturePropertyType {
    
    ID(false, new QName(OwsContext.XSD_URI, "string")),
    TEXT(false, new QName(OwsContext.XSD_URI, "string")),
    INTEGER(false, new QName(OwsContext.XSD_URI, "integer")),
    DOUBLE(false, new QName(OwsContext.XSD_URI, "double")),
    INSTANT(false, new QName(OwsContext.XSD_URI, "dateTime ")),
    POINT(true, new QName(OwsContext.GML_URI, "PointPropertyType")),
    POINT_TYPE(true, new QName(OwsContext.GML_URI, "PointPropertyType")),
    POLYGON(true, new QName(OwsContext.GML_URI, "SurfacePropertyType")),
    POLYGON_TYPE(true, new QName(OwsContext.GML_URI, "SurfacePropertyType")), // TODO: Explain that the difference is the element name, and that this references the xsd "gml:PolygonType", making this an "is a" Polygon, rather than a "has a" Polygon
    GEOMETRY_PROPERTY_TYPE(true, new QName(OwsContext.GML_URI, "GeometryPropertyType"));

    private final boolean isBoundingBoxProvider;

    private final QName qualifiedName;

    private FeaturePropertyType(final boolean isBoundingBoxProvider, final QName qualifiedName) {
        this.isBoundingBoxProvider = isBoundingBoxProvider;
        this.qualifiedName = qualifiedName;
    }

    public boolean isBoundingBoxProvider() {
        return this.isBoundingBoxProvider;
    }

    public QName getQualifiedName() {
        return qualifiedName;
    }

}
