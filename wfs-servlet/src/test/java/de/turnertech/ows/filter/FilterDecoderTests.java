package de.turnertech.ows.filter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.common.DefaultOwsContextFactory;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.gml.Feature;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.FeaturePropertyType;
import de.turnertech.ows.gml.FeatureType;
import jakarta.servlet.ServletException;

public class FilterDecoderTests {
    
    private final String ID_FILTER_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fes:Filter xmlns:fes=\"http://www.opengis.net/fes/2.0\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><fes:ResourceId rid=\"082hf3j3\"/></fes:Filter>";

    private final String MIXED_FILTER_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fes:Filter xmlns:fes=\"http://www.opengis.net/fes/2.0\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><fes:And><fes:And><fes:PropertyIsEqualTo><fes:Literal>5</fes:Literal><fes:Literal>7</fes:Literal></fes:PropertyIsEqualTo><fes:BBOX><fes:ValueReference>hazard-type</fes:ValueReference><fes:Envelope>brand</fes:Envelope></fes:BBOX></fes:And><fes:PropertyIsEqualTo><fes:Literal>5.0</fes:Literal><fes:Literal>7.5</fes:Literal></fes:PropertyIsEqualTo></fes:And></fes:Filter>";

    @Test
    void basicDecoderTest() throws XMLStreamException, ServletException {
        OwsContext owsContext = new DefaultOwsContextFactory().createOwsContext();
        String xml = "<Filter></Filter>";
        StringReader stringReader = new StringReader(xml);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader  = xmlInputFactory.createXMLStreamReader(stringReader);

        Filter decodedFilter = FilterDecoder.decode(xmlStreamReader, owsContext);
    }

    @Test
    void idFilterTest() throws XMLStreamException, ServletException {
        FeatureType featureType = new FeatureType("test", "MyFeature");
        featureType.putProperty(new FeatureProperty("hazard-type", FeaturePropertyType.DOUBLE));
        featureType.putProperty(new FeatureProperty("id", FeaturePropertyType.ID));
        
        Feature feature = featureType.createInstance();
        feature.setPropertyValue("hazard-type", 10.0);
        feature.setPropertyValue("id", "082hf3j3");

        OwsContext owsContext = new DefaultOwsContextFactory().createOwsContext();
        StringReader stringReader = new StringReader(ID_FILTER_STRING);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader  = xmlInputFactory.createXMLStreamReader(stringReader);

        final Filter decodedFilter = FilterDecoder.decode(xmlStreamReader, owsContext);
        assertNotNull(decodedFilter);
        assertNotNull(decodedFilter.getFilter());
        assertNotNull(decodedFilter.getFilter().test(feature));
    }

    @Test
    void mixedFilterTest() throws XMLStreamException, ServletException {
        FeatureType featureType = new FeatureType("test", "MyFeature");
        featureType.putProperty(new FeatureProperty("hazard-type", FeaturePropertyType.DOUBLE));
        featureType.putProperty(new FeatureProperty("id", FeaturePropertyType.ID));
        
        Feature feature = featureType.createInstance();
        feature.setPropertyValue("hazard-type", 10.0);
        feature.setPropertyValue("id", "082hf3j3");

        OwsContext owsContext = new DefaultOwsContextFactory().createOwsContext();
        StringReader stringReader = new StringReader(MIXED_FILTER_STRING);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader  = xmlInputFactory.createXMLStreamReader(stringReader);

        final Filter decodedFilter = FilterDecoder.decode(xmlStreamReader, owsContext);
        assertNotNull(decodedFilter);
        assertNotNull(decodedFilter.getFilter());


        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(outStream, StandardCharsets.UTF_8.name());

        out.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");

        FilterEncoder.encode(out, decodedFilter, owsContext);

        String xmlOut = outStream.toString(StandardCharsets.UTF_8);
        System.out.print(xmlOut);

    }

}
