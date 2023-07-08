package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlEncoder;

/**
 * Delegating Encoder
 */
public class OperatorEncoder implements XmlEncoder<Operator> {
    
    static final OperatorEncoder I = new OperatorEncoder();

    private OperatorEncoder() {}

    public void encode(final XMLStreamWriter out, final Operator operator, final OwsContext owsContext) throws XMLStreamException {
        if(operator instanceof IdOperator) {
            IdOperatorEncoder.I.encode(out, (IdOperator)operator, owsContext);
        }
        if(operator instanceof BinaryLogicOperator) {
            BinaryLogicOperatorEncoder.I.encode(out, (BinaryLogicOperator)operator, owsContext);
        }
        if(operator instanceof BinaryComparisonOperator) {
            BinaryComparisonOperatorEncoder.I.encode(out, (BinaryComparisonOperator)operator, owsContext);
        }
    }

}
