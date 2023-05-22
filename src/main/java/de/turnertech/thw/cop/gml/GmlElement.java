package de.turnertech.thw.cop.gml;

import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Logging;

public interface GmlElement {
    
    public static final String PREFIX = "gml";

    public static final String NAMESPACE = "http://www.opengis.net/gml/3.2";

    public default String toGml() {
        return toGml(PREFIX, getGmlName(), NAMESPACE);
    }

    public default String toGml(String prefix, String localName, String namespaceURI) {
        StringWriter outStream = new StringWriter();

        try {
            XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(outStream);
            writeGml(out, prefix, localName, namespaceURI);
            out.close();
        } catch (Exception e) {
            return null;
        }

        return outStream.toString();
    }

    public default void writeGml(XMLStreamWriter out) {
        writeGml(out, PREFIX, getGmlName(), NAMESPACE);
    }

    public void writeGml(XMLStreamWriter out, String prefix, String localName, String namespaceURI);

    public String getGmlName();

    public default void writeGmlStartElement(XMLStreamWriter out, String prefix, String localName, String namespaceURI) {
        try {
            if (prefix != null && localName != null && namespaceURI != null ) {
                out.writeStartElement(prefix, localName, namespaceURI);
            } else if (localName != null && namespaceURI != null) {
                out.writeStartElement(localName, namespaceURI);
            } else if (localName != null) {
                out.writeStartElement(localName);
            }
        } catch (Exception e) {
            Logging.LOG.severe("Could not get start GML Element");
        }
    }

}
