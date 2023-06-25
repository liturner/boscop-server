package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

class ExpressionDecoder {

    private ExpressionDecoder() {

    }

    public static Expression decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        Expression returnExpression = null;

        if("Literal".equals(in.getLocalName())) {
            returnExpression = LiteralDecoder.decode(in, owsContext);
        } else if("ValueReference".equals(in.getLocalName())) {
            returnExpression = ValueReferenceDecoder.decode(in, owsContext);
        }
        
        return returnExpression;
    }
    
}
