package de.turnertech.ows.filter;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.common.DefaultOwsContextFactory;
import de.turnertech.ows.common.OwsContext;
import jakarta.servlet.ServletException;

public class FilterDecoderTests {
    
    @Test
    void basicDecoderTest() throws XMLStreamException, ServletException {
        OwsContext owsContext = new DefaultOwsContextFactory().createOwsContext();
        String xml = "<Filter><And/></Filter>";
        StringReader stringReader = new StringReader(xml);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader  = xmlInputFactory.createXMLStreamReader(stringReader);

        Filter decodedFilter = FilterDecoder.decode(xmlStreamReader, owsContext);
    }

}
