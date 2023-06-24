package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.OwsContext;

public class BinaryComparisonOperatorEncoder {

    private BinaryComparisonOperatorEncoder() {
        
    }
    
    public static void encode(final XMLStreamWriter out, final BinaryComparisonOperator operator, final OwsContext owsContext) throws XMLStreamException {
        out.writeStartElement(OwsContext.FES_URI, operator.getOperatorType().toString());

        out.writeEndElement();
    }

}
