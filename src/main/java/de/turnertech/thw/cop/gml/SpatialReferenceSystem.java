package de.turnertech.thw.cop.gml;

public enum SpatialReferenceSystem {

    EPSG4327("http://www.opengis.net/def/crs/EPSG/0/4326", 2);

    private final String uri;

    private final int dimension;

    private SpatialReferenceSystem(String uri, int dimension) {
        this.uri = uri;
        this.dimension = dimension;
    }

    public String getUri() {
        return uri;
    }

    /**
     * Gtes the number of dimensions in this reference system, such as lon, lat, height.
     * @return the number of dimensions in this reference system.
     */
    public int getDimension() {
        return dimension;
    }
    
}
