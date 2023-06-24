package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

/**
 * Delegating Decoder
 */
class OperatorDecoder {
    
    public static Operator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        Operator returnOperator = null;
        System.out.println("Operator Decoder");
        if("ResourceId".equals(in.getLocalName())) {
            returnOperator = IdOperatorDecoder.decode(in, owsContext);
        } else {
            returnOperator = NonIdOperatorDecoder.decode(in, owsContext);
        }
        return returnOperator;
    }

}
