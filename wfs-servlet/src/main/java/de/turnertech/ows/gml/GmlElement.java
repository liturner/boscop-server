package de.turnertech.ows.gml;

import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;

public interface GmlElement {
    
    public static final String NAMESPACE = "http://www.opengis.net/gml/3.2";

    public default String toGml() {
        return toGml(getGmlName(), NAMESPACE, SpatialReferenceSystemRepresentation.EPSG4327_URI);
    }

    public default String toGml(String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        StringWriter outStream = new StringWriter();

        try {
            XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(outStream);
            writeGml(out, localName, namespaceURI, srs);
            out.close();
        } catch (Exception e) {
            return null;
        }

        return outStream.toString();
    }

    public default void writeGml(XMLStreamWriter out) {
        writeGml(out, getGmlName(), NAMESPACE, SpatialReferenceSystemRepresentation.EPSG4327_URI);
    }

    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs);

    public String getGmlName();

    public default void writeGmlStartElement(XMLStreamWriter out, String localName, String namespaceURI) {
        try {
            if (localName != null && namespaceURI != null) {
                out.writeStartElement(namespaceURI, localName);
            } else if (localName != null) {
                out.writeStartElement(localName);
            }
        } catch (Exception e) {
            Logging.LOG.severe("Could not get start GML Element");
        }
    }

}
