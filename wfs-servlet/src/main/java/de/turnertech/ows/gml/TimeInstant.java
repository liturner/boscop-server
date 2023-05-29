package de.turnertech.ows.gml;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;

/**
 * gml:TimeInstant
 */
public class TimeInstant implements GmlElement {

    public static final String GML_NAME = "TimeInstant";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd'T'hh:mm:ssX")
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
        .toFormatter().withZone(ZoneOffset.UTC);
    
    private Instant timePosition;

    public TimeInstant(Instant timePosition) {
        this.timePosition = timePosition;
    }

    public Instant getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(Instant timePosition) {
        this.timePosition = timePosition;
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeStartElement(GmlElement.NAMESPACE, "timePosition");
            out.writeCharacters(DATE_TIME_FORMATTER.format(timePosition));
            out.writeEndElement();
            out.writeEndElement();
        } catch (DateTimeException e) {
            Logging.LOG.severe("Could not get String for Instant in TimeInstant");
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for TimeInstant");
        }
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

    @Override
    public String toString() {
        return DATE_TIME_FORMATTER.format(timePosition);
    }
    
}
