package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

/**
 * Leaf Decoder
 * 
 * Note, must consume the input to its return element
 */
class ValueReferenceDecoder implements XmlDecoder<ValueReference> {
    
    static final ValueReferenceDecoder I = new ValueReferenceDecoder();

    private ValueReferenceDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return ValueReference.QNAME.equals(in.getName());
    }

    @Override
    public ValueReference decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        return new ValueReference(in.getElementText());
    }

}
