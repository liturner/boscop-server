package de.turnertech.ows.srs;

public class SpatialReferenceSystemRepresentation {

    public static final int URI = 0;

    public static final int URN = 2;

    public static final SpatialReferenceSystemRepresentation EPSG4327_URI = new SpatialReferenceSystemRepresentation(SpatialReferenceSystem.EPSG4326, URI);

    public static final SpatialReferenceSystemRepresentation EPSG4327_URN = new SpatialReferenceSystemRepresentation(SpatialReferenceSystem.EPSG4326, URN);

    public SpatialReferenceSystem srs;

    public int representation;

    public SpatialReferenceSystemRepresentation(SpatialReferenceSystem srs, int representation) {
        this.representation = representation;
        this.srs = srs;
    }

    public SpatialReferenceSystem getSrs() {
        return srs;
    }

}
