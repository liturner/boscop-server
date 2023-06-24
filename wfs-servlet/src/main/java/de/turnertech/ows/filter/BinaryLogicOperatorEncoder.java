package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;

public class BinaryLogicOperatorEncoder {

    private BinaryLogicOperatorEncoder() {

    }

    public static void encode(final XMLStreamWriter out, final BinaryLogicOperator operator, final OwsContext owsContext) throws XMLStreamException {
        out.writeStartElement(OwsContext.FES_URI, operator.getOperatorType().toString());

        out.writeEndElement();
    }

}
