package de.turnertech.ows.gml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.common.DefaultOwsContextFactory;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import jakarta.servlet.ServletException;

public class EnvelopeDecoderTests {
    
    private final String ENVELOPE_STRING_1 = "<gml:Envelope xmlns:gml='http://www.opengis.net/gml/3.2' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' srsName='EPSG:4326'><gml:lowerCorner>50.23 9.23</gml:lowerCorner><gml:upperCorner>50.31 9.27</gml:upperCorner></gml:Envelope>";

    @Test
    void basicDecoderTest() throws XMLStreamException, ServletException {
        OwsContext owsContext = new DefaultOwsContextFactory().createOwsContext();
        StringReader stringReader = new StringReader(ENVELOPE_STRING_1);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader in  = xmlInputFactory.createXMLStreamReader(stringReader);

        // The first element is allways "Document Start". Skip it.
        in.next();

        assertTrue(EnvelopeDecoder.canDecode(in));

        final GmlDecoderContext gmlContext = new GmlDecoderContext();
        gmlContext.getSrsDeque().push(SpatialReferenceSystem.CRS84);
        final Envelope decodedEnvelope = EnvelopeDecoder.decode(in, owsContext, gmlContext);

        assertNotNull(decodedEnvelope);
        assertNotNull(decodedEnvelope.lowerCorner);
        assertNotNull(decodedEnvelope.upperCorner);
        assertEquals(SpatialReferenceSystem.EPSG4326, decodedEnvelope.lowerCorner.getSrs());
        assertEquals(SpatialReferenceSystem.EPSG4326, decodedEnvelope.upperCorner.getSrs());
        assertEquals(9.23, decodedEnvelope.lowerCorner.getX());
        assertEquals(9.27, decodedEnvelope.upperCorner.getX());
        assertEquals(50.23, decodedEnvelope.lowerCorner.getY());
        assertEquals(50.31, decodedEnvelope.upperCorner.getY());
    }

}
