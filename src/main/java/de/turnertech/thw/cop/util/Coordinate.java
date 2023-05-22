package de.turnertech.thw.cop.util;

/**
 * @deprecated Use gml Classes
 */
@Deprecated
public class Coordinate implements PositionProvider {

    private double latitude;

    private double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }
    
}
