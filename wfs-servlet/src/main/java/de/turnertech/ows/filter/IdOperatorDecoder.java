package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

/**
 * Leaf Decoder
 */
class IdOperatorDecoder implements XmlDecoder<Operator> {
    
    static final IdOperatorDecoder I = new IdOperatorDecoder();

    private IdOperatorDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public Operator decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        System.out.println("Id Operator Decoder");
        
        String resourceId = in.getAttributeValue(null, "rid");

        return new IdOperator(new ResourceId(resourceId));
    }

}
