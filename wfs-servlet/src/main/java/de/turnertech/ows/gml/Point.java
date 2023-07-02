package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

/**
 * gml:Point
 */
public class Point implements GmlElement, BoundingBoxProvider {
    
    public static final String GML_NAME = "Point";

    private DirectPosition pos;

    public Point() {
        this(0.0, 0.0);
    }

    public Point(double x, double y) {
        this.pos = new DirectPosition(x, y);
    }

    public double getX() {
        return pos.getX();
    }

    public void setX(double x) {
        this.pos.setX(x);
    }

    public double getY() {
        return pos.getY();
    }

    public void setY(double y) {
        this.pos.setY(y);
    }

    

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeAttribute(GmlElement.NAMESPACE, "srsName", srs.toString());

            pos.writeGml(out, DirectPosition.GML_NAME, DirectPosition.NAMESPACE, srs);

            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for DirectPosition");
        }        
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

    @Override
    public Envelope getBoundingBox() {
        return new Envelope(getY() - 0.00001, getX() - 0.00001, getY() + 0.00001, getX() + 0.00001);
    }

    public DirectPosition getPos() {
        return pos;
    }

    public void setPos(DirectPosition pos) {
        this.pos = pos;
    }    

}
