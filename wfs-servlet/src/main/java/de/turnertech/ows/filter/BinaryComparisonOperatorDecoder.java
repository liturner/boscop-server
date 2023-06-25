package de.turnertech.ows.filter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

public class BinaryComparisonOperatorDecoder {
    
    private BinaryComparisonOperatorDecoder() {
        
    }

    public static BinaryComparisonOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final BinaryComparisonName binaryComparisonName = BinaryComparisonName.fromString(in.getLocalName());
        final List<Expression> expressions = new ArrayList<>(2);

        System.out.println(">" + binaryComparisonName.toString());

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                expressions.add(ExpressionDecoder.decode(in, owsContext));
            }

            if (xmlEvent == XMLStreamConstants.END_ELEMENT && binaryComparisonName.toString().equals(in.getLocalName())) {
                System.out.println("<" + binaryComparisonName.toString());
                break;
            }
        }

        return new BinaryComparisonOperator(expressions.get(0), binaryComparisonName, expressions.get(1));
    }

}
