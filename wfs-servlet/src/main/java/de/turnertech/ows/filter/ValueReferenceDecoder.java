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

    public static boolean canDecode(final XMLStreamReader in) {
        return ValueReference.QNAME.equals(in.getName());
    }

    public static ValueReference decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        return new ValueReference(in.getElementText());
    }

}
