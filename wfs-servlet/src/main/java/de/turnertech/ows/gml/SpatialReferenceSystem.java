package de.turnertech.ows.gml;

public enum SpatialReferenceSystem {

    EPSG4327("http://www.opengis.net/def/crs/EPSG/0/4326", "urn:ogc:def:crs:EPSG::4326", 2);

    private final String uri;

    private final String urn;

    private final int dimension;

    private SpatialReferenceSystem(String uri, String urn, int dimension) {
        this.uri = uri;
        this.dimension = dimension;
        this.urn = urn;
    }

    public String getUri() {
        return uri;
    }

    public String getUrn() {
        return urn;
    }

    public int getDimension() {
        return dimension;
    }
    
}
