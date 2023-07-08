package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

class ExpressionDecoder implements XmlDecoder<Expression> {

    static final ExpressionDecoder I = new ExpressionDecoder();

    private ExpressionDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public Expression decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        Expression returnExpression = null;

        if("Literal".equals(in.getLocalName())) {
            returnExpression = LiteralDecoder.I.decode(in, owsContext);
        } else if("ValueReference".equals(in.getLocalName())) {
            returnExpression = ValueReferenceDecoder.I.decode(in, owsContext);
        }
        
        return returnExpression;
    }
    
}
