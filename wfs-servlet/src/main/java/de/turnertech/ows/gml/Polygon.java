package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

/**
 * gml:Polygon
 */
public class Polygon implements GmlElement, BoundingBoxProvider {
    
    public static final String GML_NAME = "Polygon";

    private LinearRing exterior;

    public Polygon() {
        this(new LinearRing());
    }

    public Polygon(LinearRing exterior) {
        this.exterior = exterior;
    }

    public LinearRing getExterior() {
        return exterior;
    }

    public void setExterior(LinearRing exterior) {
        this.exterior = exterior;
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeAttribute("srsName", srs.toString());
            out.writeStartElement(GmlElement.NAMESPACE, "exterior");
            exterior.writeGml(out, LinearRing.GML_NAME, LinearRing.NAMESPACE, srs);
            out.writeEndElement();
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for LinearRing");
        }        
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return exterior.getBoundingBox();
    }

}
