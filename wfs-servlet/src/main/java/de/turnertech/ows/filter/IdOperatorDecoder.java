package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

/**
 * Leaf Decoder
 */
public class IdOperatorDecoder {
    
    public static Operator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        System.out.println("Id Operator Decoder");
        
        String resourceId = in.getAttributeValue(null, "rid");

        return new IdOperator(new ResourceId(resourceId));
    }

}
