package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

/**
 * gml:pos
 * 
 * A location with a spatial reference. This is not "Geometry", but rather an actual position.
 * 
 * This implementation maintains a list of the size of the {@link SpatialReferenceSystem#getDimension()}.
 * 
 * For convenience, and to help prevent errors in axis order, X and Y getters and setters exist. This should
 * make dealing with actual position vs coordinate representation easier.
 */
public class DirectPosition implements GmlElement {
    
    public static final String GML_NAME = "pos";

    private final double[] pos;

    /** 
     * Positions do not change often, but will be accessed very often as strings. It is significantly cheaper to just keep both
     * a string and a numeric representation up to date.
     */
    private final String[] posString;

    private final SpatialReferenceSystem srs;

    public DirectPosition() {
        this(0.0, 0.0);
    }

    public DirectPosition(double x, double y) {
        this(SpatialReferenceSystem.EPSG4326, x, y);
    }

    public DirectPosition(SpatialReferenceSystem srs, double x, double y) {
        this.srs = srs;
        this.pos = new double[srs.getDimension()];
        this.posString = new String[srs.getDimension()];
        this.setX(x);
        this.setY(y);
    }

    public double getX() {
        return pos[srs.getXIndex()];
    }

    public void setX(double x) {
        this.pos[srs.getXIndex()] = x;
        this.posString[srs.getXIndex()] = Double.toString(x);
    }

    public double getY() {
        return pos[srs.getYIndex()];
    }

    public void setY(double y) {
        this.pos[srs.getYIndex()] = y;
        this.posString[srs.getYIndex()] = Double.toString(y);
    }

    public SpatialReferenceSystem getSrs() {
        return srs;
    }

    @Override
    public String toString() {
        return String.join(" ", posString);
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srsRepresentation) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeAttribute("srsDimension", Byte.toString(srsRepresentation.getSrs().getDimension()));
            out.writeCharacters(this.toString());
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for DirectPosition");
        }
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

}
