package de.turnertech.ows.filter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.common.DefaultOwsContextFactory;
import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.gml.GmlDecoderContext;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import jakarta.servlet.ServletException;

public class BinarySpatialOperatorDecoderTests {
    
    private final String ENVELOPE_STRING_1 = "<fes:BBOX xmlns:fes='http://www.opengis.net/fes/2.0' xmlns:gml='http://www.opengis.net/gml/3.2'><gml:Envelope srsName='EPSG:4326'><gml:lowerCorner>50.23 9.23</gml:lowerCorner><gml:upperCorner>50.31 9.27</gml:upperCorner></gml:Envelope></fes:BBOX>";

    @Test
    void basicDecoderTest() throws XMLStreamException, ServletException {
        OwsContext owsContext = new DefaultOwsContextFactory().createOwsContext();
        StringReader stringReader = new StringReader(ENVELOPE_STRING_1);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        DepthXMLStreamReader in  = new DepthXMLStreamReader(xmlInputFactory.createXMLStreamReader(stringReader));

        // The first element is allways "Document Start". Skip it.
        in.next();

        assertTrue(BinarySpatialOperatorDecoder.I.canDecode(in));

        final GmlDecoderContext gmlContext = new GmlDecoderContext();
        gmlContext.getSrsDeque().push(SpatialReferenceSystem.CRS84);
        final BinarySpatialOperator filter = BinarySpatialOperatorDecoder.I.decode(in, owsContext);

        assertNotNull(filter);
        assertNotNull(filter.operatorType);
        assertNotNull(filter.operand2);
        assertNull(filter.operand1);

    }

}
