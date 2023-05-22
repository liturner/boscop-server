package de.turnertech.thw.cop.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Logging;

/**
 * gml:pos
 */
public class DirectPosition implements GmlElement {
    
    public static final String GML_NAME = "pos";

    private double[] pos;

    private SpatialReferenceSystem srs;

    public DirectPosition() {
        this(0.0, 0.0);
    }

    public DirectPosition(double x, double y) {
        this.pos = new double[]{x, y};
        this.srs = SpatialReferenceSystem.EPSG4327;
    }

    public double getX() {
        return pos[0];
    }

    public void setX(double x) {
        this.pos[0] = x;
    }

    public double getY() {
        return pos[1];
    }

    public void setY(double y) {
        this.pos[1] = y;
    }

    public SpatialReferenceSystem getSrs() {
        return srs;
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeCharacters(Double.toString(getX()) + " " + Double.toString(getY()));
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
