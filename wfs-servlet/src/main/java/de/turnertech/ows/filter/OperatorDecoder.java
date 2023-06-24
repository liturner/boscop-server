package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

/**
 * Delegating Decoder
 */
class OperatorDecoder {
    
    public static Operator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        System.out.println("Operator Decoder");
        if("ResourceId".equals(in.getLocalName())) {
            System.out.println("ResourceId (Go to leaf decoder)");
        } else {
            System.out.println("Not ResourceId (Go to next full delegating decoder)");
        }
        return null;
    }

}
