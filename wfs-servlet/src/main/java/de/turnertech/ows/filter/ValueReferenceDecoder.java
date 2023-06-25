package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

/**
 * Leaf Decoder
 * 
 * Note, must consume the input to its return element
 */
class ValueReferenceDecoder {
    
    private ValueReferenceDecoder() {

    }

    public static ValueReference decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        System.out.println(">" + "ValueReference");
        final ValueReference returnValueReference = new ValueReference(in.getElementText());
        System.out.println("<" + "ValueReference");
        return returnValueReference;
    }

}
