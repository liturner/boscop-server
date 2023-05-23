package de.turnertech.thw.cop.gml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.util.Coordinate;
import de.turnertech.thw.cop.util.PositionProvider;

public class BoundingBox implements GmlElement {

    public static final String GML_NAME = "boundedBy";

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

    public boolean intersects(BoundingBox other) {
        if(this.north < other.south) return false;
        if(this.south > other.north) return false;
        if(this.east < other.west) return false;
        if(this.west > other.east) return false;
        return true;
    }

    public static BoundingBox from(BoundingBox other) {
        return new BoundingBox(other.south, other.west, other.north, other.east);
    }

    public static BoundingBox from(DirectPositionList posList) {
        double maxSouth = Double.POSITIVE_INFINITY;
        double maxWest = Double.POSITIVE_INFINITY;
        double maxNorth = Double.NEGATIVE_INFINITY;
        double maxEast = Double.NEGATIVE_INFINITY;

        for(DirectPosition position : posList) {
            if(position.getY() > maxNorth) maxNorth = position.getY();
            if(position.getY() < maxSouth) maxSouth = position.getY();
            if(position.getX() > maxEast) maxEast = position.getX();
            if(position.getX() < maxWest) maxWest = position.getX();
        }

        // Catch BBOX with a size of 0 (causes errors in many clients, happens with only 1 point)
        if(maxSouth == maxNorth) {
            maxNorth += 0.00001;
            maxSouth -= 0.00001;
        }
        if(maxEast == maxWest) {
            maxEast += 0.00001;
            maxWest -= 0.00001;
        }

        return new BoundingBox(maxSouth, maxWest, maxNorth, maxEast);
    }

    public static BoundingBox from(PositionProvider... positions) {
        return from(Arrays.asList(positions));
    }

    public static BoundingBox from(List<? extends PositionProvider> positions) {
        double maxSouth = Double.POSITIVE_INFINITY;
        double maxWest = Double.POSITIVE_INFINITY;
        double maxNorth = Double.NEGATIVE_INFINITY;
        double maxEast = Double.NEGATIVE_INFINITY;

        for(PositionProvider position : positions) {
            if(position.getLatitude() > maxNorth) maxNorth = position.getLatitude();
            if(position.getLatitude() < maxSouth) maxSouth = position.getLatitude();
            if(position.getLongitude() > maxEast) maxEast = position.getLongitude();
            if(position.getLongitude() < maxWest) maxWest = position.getLongitude();
        }

        // Catch BBOX with a size of 0 (causes errors in many clients, happens with only 1 point)
        if(maxSouth == maxNorth) {
            maxNorth += 0.00001;
            maxSouth -= 0.00001;
        }
        if(maxEast == maxWest) {
            maxEast += 0.00001;
            maxWest -= 0.00001;
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

    public void expandToFit(BoundingBox other) {
        if(other.north > north) north = other.north;
        if(other.south < south) south = other.south;
        if(other.east > east) east = other.east;
        if(other.west < west) west = other.west;
    }

    public Coordinate centerPoint() {
        return new Coordinate(south + (north - south), west + (east - west));
    }

    /**
     * @return the south
     */
    public double getSouth() {
        return south;
    }

    /**
     * @return the west
     */
    public double getWest() {
        return west;
    }

    /**
     * @return the north
     */
    public double getNorth() {
        return north;
    }

    /**
     * @return the east
     */
    public double getEast() {
        return east;
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        DecimalFormat decimalFormat = new DecimalFormat("0.", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(8);

        try {
            writeGmlStartElement(out, localName, namespaceURI);
                out.writeStartElement(GmlElement.NAMESPACE, "Envelope");
                out.writeAttribute(GmlElement.NAMESPACE, "srsName", SpatialReferenceSystem.EPSG4327.getUri());
                    out.writeStartElement(GmlElement.NAMESPACE, "lowerCorner");
                        out.writeCharacters(decimalFormat.format(south) + " " + decimalFormat.format(west));
                    out.writeEndElement();
                    out.writeStartElement(GmlElement.NAMESPACE, "upperCorner");
                        out.writeCharacters(decimalFormat.format(north) + " " + decimalFormat.format(east));
                    out.writeEndElement();
                out.writeEndElement();
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for BoundingBox");
        }
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

}
