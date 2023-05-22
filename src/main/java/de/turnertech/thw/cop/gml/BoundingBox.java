package de.turnertech.thw.cop.gml;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import de.turnertech.thw.cop.util.Coordinate;
import de.turnertech.thw.cop.util.PositionProvider;

public class BoundingBox {

    protected double south;
    
    protected double west;
    
    protected double north;
    
    protected double east; 

    public BoundingBox(double south, double west, double north, double east) {
        this.south = south;
        this.west = west;
        this.north = north;
        this.east = east;
    }

    public boolean contains(List<? extends PositionProvider> positions) {
        for(PositionProvider position : positions) {
            if(this.contains(position)) return true;
        }
        return false;
    }

    public boolean contains(PositionProvider position) {
        return contains(position.getLatitude(), position.getLongitude());
    }

    public boolean contains(double latitude, double longitute) {
        return !(latitude > north || latitude < south || longitute > east || longitute < west);
    }

    public String toGmlString() {
        StringWriter stringWriter = new StringWriter();
        stringWriter.write("<gml:boundedBy><gml:Envelope srsName=\"urn:ogc:def:crs:EPSG::4326\"><gml:lowerCorner>");
        stringWriter.write(String.valueOf(south));
        stringWriter.write(" ");
        stringWriter.write(String.valueOf(west));
        stringWriter.write("</gml:lowerCorner><gml:upperCorner>");
        stringWriter.write(String.valueOf(north));
        stringWriter.write(" ");
        stringWriter.write(String.valueOf(east));
        stringWriter.write("</gml:upperCorner></gml:Envelope></gml:boundedBy>");
        return stringWriter.toString();
    }

    public static BoundingBox from(PositionProvider... positions) {
        return from(Arrays.asList(positions));
    }

    public static BoundingBox from(List<? extends PositionProvider> positions) {
        double maxSouth = Double.MAX_VALUE;
        double maxWest = Double.MAX_VALUE;
        double maxNorth = Double.MIN_VALUE;
        double maxEast = Double.MIN_VALUE;

        for(PositionProvider position : positions) {
            if(position.getLatitude() > maxNorth) maxNorth = position.getLatitude();
            if(position.getLatitude() < maxSouth) maxSouth = position.getLatitude();
            if(position.getLongitude() > maxEast) maxEast = position.getLongitude();
            if(position.getLongitude() < maxWest) maxWest = position.getLongitude();
        }

        // Catch BBOX with a size of 0 (causes errors in many clients, happens with only 1 point)
        if(maxSouth == maxNorth) {
            maxNorth += 0.0001;
            maxSouth -= 0.0001;
        }
        if(maxEast == maxWest) {
            maxEast += 0.0001;
            maxWest -= 0.0001;
        }

        return new BoundingBox(maxSouth, maxWest, maxNorth, maxEast);
    }

    public void expandToFit(List<? extends PositionProvider> positions) {
        for(PositionProvider position : positions) {
            if(position.getLatitude() > north) north = position.getLatitude();
            if(position.getLatitude() < south) south = position.getLatitude();
            if(position.getLongitude() > east) east = position.getLongitude();
            if(position.getLongitude() < west) west = position.getLongitude();
        }
    }

    public Coordinate centerPoint() {
        return new Coordinate(south + (north - south), west + (east - west));
    }

}
