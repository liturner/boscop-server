package de.turnertech.ows.filter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

class BinaryLogicOperatorDecoder implements XmlDecoder<BinaryLogicOperator> {
    
    static final BinaryLogicOperatorDecoder I = new BinaryLogicOperatorDecoder();

    private BinaryLogicOperatorDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public BinaryLogicOperator decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final BinaryLogicType operatorType = BinaryLogicType.fromString(in.getLocalName());
        final List<NonIdOperator> operators = new ArrayList<>(2);
        
        if(operatorType == null) {
            throw new XMLStreamException("Unknown Binary Logic Operator " + in.getLocalName(), in.getLocation());
        }

        System.out.println(">" + operatorType.toString());

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                operators.add(NonIdOperatorDecoder.I.decode(in, owsContext));
            }

            if (xmlEvent == XMLStreamConstants.END_ELEMENT && operatorType.toString().equals(in.getLocalName())) {
                System.out.println("<" + operatorType.toString());
                break;
            }
        }

        if(operators.size() < 2) {
            throw new XMLStreamException("Binary Logic Operator does not contain two Operators.", in.getLocation());
        }

        return new BinaryLogicOperator(operators.get(0), operatorType, operators.get(1));
    }

}
