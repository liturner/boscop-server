package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

/**
 * Leaf Decoder
 */
class SpatialOperatorDecoder implements XmlDecoder<SpatialOperator> {
    
    static SpatialOperatorDecoder I = new SpatialOperatorDecoder();

    private SpatialOperatorDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return BinarySpatialOperatorDecoder.I.canDecode(in);
    }

    @Override
    public SpatialOperator decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        SpatialOperator returnSpatialOperator = null;

        if(BinarySpatialOperatorDecoder.I.canDecode(in)) {
            returnSpatialOperator = BinarySpatialOperatorDecoder.I.decode(in, owsContext);
        }

        return returnSpatialOperator;
    }

}
