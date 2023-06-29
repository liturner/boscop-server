package de.turnertech.ows.filter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.gml.EnvelopeDecoder;

public class BinarySpatialOperatorDecoder {
    
    private BinarySpatialOperatorDecoder() {

    }

    public static boolean canDecode(final QName element) {
        return SpatialOperatorName.valueOf(element) != null;
    }

    public static BinarySpatialOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final SpatialOperatorName spatialOperatorName = SpatialOperatorName.valueOf(in.getName());
        final List<ValueReference> valueReferences = new ArrayList<>(2);

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                if(ValueReferenceDecoder.canDecode(in)) {
                    valueReferences.add(ValueReferenceDecoder.decode(in, owsContext));
                } else if (EnvelopeDecoder.canDecode(in)) {
                    // TODO
                }
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT && spatialOperatorName.toString().equals(in.getLocalName())) {
                break;
            }
        }

        return new BinarySpatialOperator(null, spatialOperatorName, null);
    }

}
