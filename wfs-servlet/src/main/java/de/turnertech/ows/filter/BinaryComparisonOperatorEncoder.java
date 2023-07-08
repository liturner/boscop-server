package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlEncoder;

/**
 * Partially Delegating Encoder
 */
class BinaryComparisonOperatorEncoder implements XmlEncoder<BinaryComparisonOperator> {

    static final BinaryComparisonOperatorEncoder I = new BinaryComparisonOperatorEncoder();

    private BinaryComparisonOperatorEncoder() {}
    
    @Override
    public void encode(final XMLStreamWriter out, final BinaryComparisonOperator operator, final OwsContext owsContext) throws XMLStreamException {
        out.writeStartElement(OwsContext.FES_URI, operator.getOperatorType().toString());

        ExpressionEncoder.I.encode(out, operator.getLeftExpression(), owsContext);
        ExpressionEncoder.I.encode(out, operator.getRightExpression(), owsContext);        

        out.writeEndElement();
    }

}
