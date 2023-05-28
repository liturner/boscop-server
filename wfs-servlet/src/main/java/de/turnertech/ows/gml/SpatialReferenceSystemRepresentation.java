package de.turnertech.ows.gml;

public class SpatialReferenceSystemRepresentation {

    public static final int URI = 0;

    public static final int URN = 2;

    public static final SpatialReferenceSystemRepresentation EPSG4327_URI = new SpatialReferenceSystemRepresentation(SpatialReferenceSystem.EPSG4327, URI);

    public static final SpatialReferenceSystemRepresentation EPSG4327_URN = new SpatialReferenceSystemRepresentation(SpatialReferenceSystem.EPSG4327, URN);

    public SpatialReferenceSystem srs;

    public int representation;

    public SpatialReferenceSystemRepresentation(SpatialReferenceSystem srs, int representation) {
        this.representation = representation;
        this.srs = srs;
    }

}
