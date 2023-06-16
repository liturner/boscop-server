package de.turnertech.ows.srs;

public enum SpatialReferenceSystem {

    /** WGS84 */
    EPSG4326("EPSG:4326", "http://www.opengis.net/def/crs/EPSG/0/4326", "urn:ogc:def:crs:EPSG::4326", (byte)2, (byte)1, (byte)0),

    /** WebMercator */
    EPSG3857("EPSG:3857", "http://www.opengis.net/def/crs/EPSG/0/3857", "urn:ogc:def:crs:EPSG::3857", (byte)2, (byte)0, (byte)1);

    private final String code;
    
    private final String uri;

    private final String urn;

    private final byte dimension;

    private final byte xIndex;

    private final byte yIndex;

    private SpatialReferenceSystem(String code, String uri, String urn, byte dimension, byte xIndex, byte yIndex) {
        this.code = code;
        this.uri = uri;
        this.dimension = dimension;
        this.urn = urn;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public String getCode() {
        return code;
    }

    public String getUri() {
        return uri;
    }

    public String getUrn() {
        return urn;
    }

    public byte getDimension() {
        return dimension;
    }

    public byte getXIndex() {
        return xIndex;
    }

    public byte getYIndex() {
        return yIndex;
    }
    
}
