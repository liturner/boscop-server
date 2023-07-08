package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

/**
 * Delegating Decoder
 */
class LogicalOperatorDecoder implements XmlDecoder<LogicalOperator> {
    
    static final LogicalOperatorDecoder I = new LogicalOperatorDecoder();

    private LogicalOperatorDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public LogicalOperator decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        LogicalOperator returnOperator = null;
        if("And".equals(in.getLocalName()) || "Or".equals(in.getLocalName())) {
            returnOperator = BinaryLogicOperatorDecoder.I.decode(in, owsContext);
        } else if("Not".equals(in.getLocalName())) {
            System.out.println("Go Unary Logic Operator Decoder");
        }
        return returnOperator;
    }

}
