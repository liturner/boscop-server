package de.turnertech.ows.filter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

/**
 * Leaf Decoder
 */
public class SpatialOperatorDecoder {
    
    private SpatialOperatorDecoder() {

    }

    public static boolean canDecode(final XMLStreamReader in) {
        return BinarySpatialOperatorDecoder.canDecode(in);
    }

    public static SpatialOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        SpatialOperator returnSpatialOperator = null;

        if(BinarySpatialOperatorDecoder.canDecode(in)) {
            returnSpatialOperator = BinarySpatialOperatorDecoder.decode(in, owsContext);
        }

        return returnSpatialOperator;
    }

}
