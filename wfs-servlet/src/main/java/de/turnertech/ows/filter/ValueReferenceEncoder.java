package de.turnertech.ows.filter;

import java.util.Objects;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;

public class ValueReferenceEncoder {
    
    private ValueReferenceEncoder() {
        
    }

    public static void encode(final XMLStreamWriter out, final ValueReference valueReference, final OwsContext owsContext) throws XMLStreamException {
        out.writeStartElement(OwsContext.FES_URI, "ValueReference");

        if(valueReference.toString() == null) {
            out.writeAttribute(OwsContext.XSI_URI, "nil", "true");
        } else {
            out.writeCharacters(Objects.toString(valueReference.toString()));
        }

        out.writeEndElement();
    }

}
