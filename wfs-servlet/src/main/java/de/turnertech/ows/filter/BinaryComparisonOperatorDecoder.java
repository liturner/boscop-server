package de.turnertech.ows.filter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

public class BinaryComparisonOperatorDecoder implements XmlDecoder<BinaryComparisonOperator> {
    
    static final BinaryComparisonOperatorDecoder I = new BinaryComparisonOperatorDecoder();

    private BinaryComparisonOperatorDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public BinaryComparisonOperator decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final BinaryComparisonName binaryComparisonName = BinaryComparisonName.fromString(in.getLocalName());
        final List<Expression> expressions = new ArrayList<>(2);

        if(binaryComparisonName == null) {
            throw new XMLStreamException("Unknown Binary Comparison Operator " + in.getLocalName(), in.getLocation());
        }

        System.out.println(">" + binaryComparisonName.toString());

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                expressions.add(ExpressionDecoder.I.decode(in, owsContext));
            }

            if (xmlEvent == XMLStreamConstants.END_ELEMENT && binaryComparisonName.toString().equals(in.getLocalName())) {
                System.out.println("<" + binaryComparisonName.toString());
                break;
            }
        }

        if(expressions.size() < 2) {
            throw new XMLStreamException("Binary Comparison Operator does not contain two expressions.", in.getLocation());
        }

        return new BinaryComparisonOperator(expressions.get(0), binaryComparisonName, expressions.get(1));
    }

}
