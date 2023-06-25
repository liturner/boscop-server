package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

/**
 * Delegating Decoder
 */
class LogicalOperatorDecoder {
    
    private LogicalOperatorDecoder() {

    }

    public static LogicalOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        LogicalOperator returnOperator = null;
        if("And".equals(in.getLocalName()) || "Or".equals(in.getLocalName())) {
            returnOperator = BinaryLogicOperatorDecoder.decode(in, owsContext);
        } else if("Not".equals(in.getLocalName())) {
            System.out.println("Go Unary Logic Operator Decoder");
        }
        return returnOperator;
    }

}
