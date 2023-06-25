package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;

/**
 * Delegating Encoder
 */
public class ExpressionEncoder {
    
    private ExpressionEncoder() {
        
    }

    public static void encode(final XMLStreamWriter out, final Expression expression, final OwsContext owsContext) throws XMLStreamException {
        if(expression instanceof Literal) {
            LiteralEncoder.encode(out, (Literal)expression, owsContext);
        }
        if(expression instanceof ValueReference) {
            ValueReferenceEncoder.encode(out, (ValueReference)expression, owsContext);
        }
    }

}
