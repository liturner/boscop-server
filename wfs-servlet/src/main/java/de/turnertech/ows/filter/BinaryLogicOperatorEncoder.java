package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlEncoder;

public class BinaryLogicOperatorEncoder implements XmlEncoder<BinaryLogicOperator> {

    static final BinaryLogicOperatorEncoder I = new BinaryLogicOperatorEncoder();

    private BinaryLogicOperatorEncoder() {}

    @Override
    public void encode(final XMLStreamWriter out, final BinaryLogicOperator operator, final OwsContext owsContext) throws XMLStreamException {
        out.writeStartElement(OwsContext.FES_URI, operator.getOperatorType().toString());

        OperatorEncoder.I.encode(out, operator.getLeftOperand(), owsContext);
        OperatorEncoder.I.encode(out, operator.getRightOperand(), owsContext);

        out.writeEndElement();
    }

}
