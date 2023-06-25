package de.turnertech.ows.filter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

class BinaryLogicOperatorDecoder {
    
    private BinaryLogicOperatorDecoder() {
        
    }

    public static BinaryLogicOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final BinaryLogicType operatorType = BinaryLogicType.fromString(in.getLocalName());
        final List<NonIdOperator> operators = new ArrayList<>(2);
        
        System.out.println(">" + operatorType.toString());

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                operators.add(NonIdOperatorDecoder.decode(in, owsContext));
            }

            if (xmlEvent == XMLStreamConstants.END_ELEMENT && operatorType.toString().equals(in.getLocalName())) {
                System.out.println("<" + operatorType.toString());
                break;
            }
        }

        return new BinaryLogicOperator(operators.get(0), operatorType, operators.get(1));
    }
}
