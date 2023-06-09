package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

/**
 * gml:TimePeriod
 */
public class TimePeriod implements GmlElement {
    
    public static final String GML_NAME = "TimePeriod";

    private TimeInstant begin;

    private TimeInstant end;

    public TimePeriod(TimeInstant begin, TimeInstant end) {
        this.begin = begin;
        this.end = end;
    }

    public TimeInstant getBegin() {
        return begin;
    }

    public void setBegin(TimeInstant begin) {
        this.begin = begin;
    }

    public TimeInstant getEnd() {
        return end;
    }

    public void setEnd(TimeInstant end) {
        this.end = end;
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeStartElement(GmlElement.NAMESPACE, "beginPosition");
            out.writeCharacters(begin.toString());
            out.writeEndElement();
            out.writeStartElement(GmlElement.NAMESPACE, "endPosition");
            out.writeCharacters(end.toString());
            out.writeEndElement();
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for TimeInstant");
        }
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

}
