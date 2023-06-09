package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

/**
 * <p>gml:CircleByCentrePoint</p>
 */
public class Circle implements GmlElement {
    
    public static final String GML_NAME = "Curve";

    private DirectPosition pos;

    private Distance radius;

    public Circle(double x, double y, double radius) {
        this.pos = new DirectPosition(x, y);
        this.radius = Distance.fromMetres(radius);
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

    public double getRadius() {
        return radius.getValue();
    }

    public void setRadius(double radius) {
        this.radius = Distance.fromMetres(radius);
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeStartElement(GmlElement.NAMESPACE, "segments");
            out.writeStartElement(GmlElement.NAMESPACE, "CircleByCenterPoint");
            out.writeAttribute("numArc", "1");

            radius.writeGml(out, GmlElement.NAMESPACE, "radius", srs);

            out.writeEndElement();
            out.writeEndElement();
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for Circle");
        }
        
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

}
