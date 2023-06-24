package de.turnertech.ows.filter;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.common.DefaultOwsContextFactory;
import de.turnertech.ows.common.OwsContext;
import jakarta.servlet.ServletException;

public class FilterEncoderTests {
    
    @Test
    void basicFilterEncoderTest() throws XMLStreamException, FactoryConfigurationError, ServletException {
        
        BinaryComparisonOperator o1 = new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal("7"));
        BinaryComparisonOperator o3 = new BinaryComparisonOperator(new ValueReference("hazard-type"), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal("brand"));
        BinaryLogicOperator o2 = new BinaryLogicOperator(o1, BinaryLogicType.AND, o3);
        BinaryLogicOperator and2 = new BinaryLogicOperator(o2, BinaryLogicType.AND, o1);
        Filter outFilter = new Filter(and2);
        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(outStream, StandardCharsets.UTF_8.name());
        OwsContext owsContext = new DefaultOwsContextFactory().createOwsContext();

        out.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");

        FilterEncoder.encode(out, outFilter, owsContext);

        String xmlOut = outStream.toString(StandardCharsets.UTF_8);
        System.out.print(xmlOut);
    }

    @Test
    void idTest() throws XMLStreamException, FactoryConfigurationError, ServletException {
        Filter outFilter = new Filter(new IdOperator(new ResourceId("082hf3j3")));
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(outStream, StandardCharsets.UTF_8.name());
        OwsContext owsContext = new DefaultOwsContextFactory().createOwsContext();

        out.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");

        FilterEncoder.encode(out, outFilter, owsContext);

        String xmlOut = outStream.toString(StandardCharsets.UTF_8);
        System.out.print(xmlOut);
        
        ;
    }

}
