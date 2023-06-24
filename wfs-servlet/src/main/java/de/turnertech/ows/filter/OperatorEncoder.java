package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;

/**
 * Delegating Encoder
 */
public class OperatorEncoder {
    
    private OperatorEncoder() {
        
    }

    public static void encode(final XMLStreamWriter out, final Operator operator, final OwsContext owsContext) throws XMLStreamException {
        if(operator instanceof IdOperator) {
            IdOperatorEncoder.encode(out, (IdOperator)operator, owsContext);
        }
        if(operator instanceof BinaryLogicOperator) {
            BinaryLogicOperatorEncoder.encode(out, (BinaryLogicOperator)operator, owsContext);
        }
        if(operator instanceof BinaryComparisonOperator) {
            BinaryComparisonOperatorEncoder.encode(out, (BinaryComparisonOperator)operator, owsContext);
        }
    }

}
