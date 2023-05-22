package de.turnertech.thw.cop.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Logging;

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
    public void writeGml(XMLStreamWriter out, String prefix, String localName, String namespaceURI) {
        try {
            writeGmlStartElement(out, prefix, localName, namespaceURI);
            out.writeStartElement(GmlElement.PREFIX, "segments", GmlElement.NAMESPACE);
            out.writeStartElement(GmlElement.PREFIX, "CircleByCenterPoint", GmlElement.NAMESPACE);
            out.writeAttribute("numArc", "1");

            radius.writeGml(out, GmlElement.PREFIX, "radius", GmlElement.NAMESPACE);

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
