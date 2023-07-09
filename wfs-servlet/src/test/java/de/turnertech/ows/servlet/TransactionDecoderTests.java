package de.turnertech.ows.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.common.DefaultOwsContextFactory;
import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import jakarta.servlet.ServletException;

public class TransactionDecoderTests {
    
    private static final String TEST1 = "<Transaction xmlns='http://www.opengis.net/wfs/2.0' service='WFS' version='2.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0/wfs.xsd'><Insert><Hazard xmlns='urn:ns:de:turnertech:boscop'><geometry><Point xmlns='http://www.opengis.net/gml/3.2' srsName='http://www.opengis.net/def/crs/EPSG/0/4326'><pos srsDimension='2'>48.73 11.18</pos></Point></geometry><hazardType>hazard-acute</hazardType></Hazard></Insert></Transaction>";

    @Test
    void decodeTest() throws XMLStreamException, ServletException {
        final XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
        final DepthXMLStreamReader in = new DepthXMLStreamReader(xmlInputFactory.createXMLStreamReader(new StringReader(TEST1)));

        final DefaultOwsContextFactory owsContextFactory = new DefaultOwsContextFactory();
        final OwsContext owsContext = owsContextFactory.createOwsContext();

        // TODO: We need to add feature types to the context, or the Feature Type decoder cannot work.

        in.next();
        assertTrue(TransactionDecoder.I.canDecode(in));

        final Transaction transaction = TransactionDecoder.I.decode(in, owsContext);
        assertNotNull(transaction);
        assertEquals(1, transaction.getActions().size());
    }

}
