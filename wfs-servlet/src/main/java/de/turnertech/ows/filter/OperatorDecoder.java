package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

/**
 * Delegating Decoder
 */
class OperatorDecoder implements XmlDecoder<Operator> {
    
    static final OperatorDecoder I = new OperatorDecoder();

    private OperatorDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public Operator decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        Operator returnOperator = null;
        if("ResourceId".equals(in.getLocalName())) {
            returnOperator = IdOperatorDecoder.I.decode(in, owsContext);
        } else {
            returnOperator = NonIdOperatorDecoder.I.decode(in, owsContext);
        }
        return returnOperator;
    }

}
