package de.turnertech.ows.filter;

import java.util.Objects;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;

/**
 * Encoder
 */
public class LiteralEncoder {

    private LiteralEncoder() {
        
    }

    public static void encode(final XMLStreamWriter out, final Literal literal, final OwsContext owsContext) throws XMLStreamException {
        out.writeStartElement(OwsContext.FES_URI, "Literal");

        if(literal.get() == null) {
            out.writeAttribute(OwsContext.XSI_URI, "nil", "true");
        } else {
            out.writeCharacters(Objects.toString(literal.get()));
        }

        out.writeEndElement();
    }
    
}
