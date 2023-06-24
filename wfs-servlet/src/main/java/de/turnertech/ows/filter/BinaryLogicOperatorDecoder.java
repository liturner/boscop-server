package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

public class BinaryLogicOperatorDecoder {
    
    public static BinaryLogicOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        System.out.println("Binary Logic Operator Decoder");

        BinaryLogicType operatorType = null;
        NonIdOperator left = null;
        NonIdOperator right = null;
        
        if("And".equals(in.getLocalName())) {
            operatorType = BinaryLogicType.AND;
        } else if("Or".equals(in.getLocalName())) {
            operatorType = BinaryLogicType.OR;
        } else {
            return null;
        }

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                System.out.println("Start Element: " + in.getLocalName());

                left = NonIdOperatorDecoder.decode(in, owsContext);
            }

            if (xmlEvent == XMLStreamConstants.END_ELEMENT) {
                if(operatorType.toString().equals(in.getLocalName())) {
                    break;
                }
            }
        }

        return new BinaryLogicOperator(left, operatorType, right);
    }
}
