package de.turnertech.ows.gml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;

public class BoundingBox implements GmlElement {

    public static final String GML_NAME = "boundedBy";

    protected double south;
    
    protected double west;
    
    protected double north;
    
    protected double east;

    // Use with care! This is an inverted and unusable box!
    public BoundingBox() {
        this.south = Double.POSITIVE_INFINITY;
        this.west = Double.POSITIVE_INFINITY;
        this.north = Double.NEGATIVE_INFINITY;
        this.east = Double.NEGATIVE_INFINITY;
    }

    public BoundingBox(double south, double west, double north, double east) {
        this.south = south;
        this.west = west;
        this.north = north;
        this.east = east;
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

    public void expandToFit(BoundingBox other) {
        if(other == null) return;
        if(other.north > north) north = other.north;
        if(other.south < south) south = other.south;
        if(other.east > east) east = other.east;
        if(other.west < west) west = other.west;
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
