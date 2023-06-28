package de.turnertech.ows.filter;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

public class BinarySpatialOperatorDecoder {
    
    private BinarySpatialOperatorDecoder() {

    }

    public static boolean canDecode(final QName element) {
        return SpatialOperatorName.valueOf(element) != null;
    }

    public static BinarySpatialOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final SpatialOperatorName spatialOperatorName = SpatialOperatorName.valueOf(in.getName());



        return new BinarySpatialOperator(null, spatialOperatorName, null);
    }

}
