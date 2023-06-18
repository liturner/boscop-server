package de.turnertech.ows.gml;

import javax.xml.namespace.QName;

import de.turnertech.ows.common.OwsContext;

/**
 * <p>Describes a single feature property, including aspects such as minimum occurences, XSD QNames or possible 
 * values etc.</p>
 * 
 * <p>For many GML types, there are often multiple entries such as "POINT" and "POINT_TYPE". These have an
 * impact on how GML will be interpreted. e.g. a Property of type POINT "has a" gml:Point as a nested element,
 * whereas POINT_TYPE "is a" gml:Point, simply with a different element name. Be cautious and do not let the
 * GML QName fool you, the naming is often misleading.</p>
 * 
 * <p>For more information, lookup the GML Simple Features Profile.</p>
 */
public enum FeaturePropertyType {
    
    ID(false, new QName(OwsContext.XSD_URI, "string")),
    TEXT(false, new QName(OwsContext.XSD_URI, "string")),
    INTEGER(false, new QName(OwsContext.XSD_URI, "integer")),
    DOUBLE(false, new QName(OwsContext.XSD_URI, "double")),
    INSTANT(false, new QName(OwsContext.XSD_URI, "dateTime ")),
    POINT(true, new QName(OwsContext.GML_URI, "PointPropertyType")),
    POINT_TYPE(true, new QName(OwsContext.GML_URI, "Point")),
    POLYGON(true, new QName(OwsContext.GML_URI, "SurfacePropertyType")),
    POLYGON_TYPE(true, new QName(OwsContext.GML_URI, "Polygon")),
    GEOMETRY(true, new QName(OwsContext.GML_URI, "GeometryPropertyType"));

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
