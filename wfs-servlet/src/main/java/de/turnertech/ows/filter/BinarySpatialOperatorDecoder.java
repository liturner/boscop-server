package de.turnertech.ows.filter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.gml.Envelope;
import de.turnertech.ows.gml.EnvelopeDecoder;
import de.turnertech.ows.gml.GmlDecoderContext;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class BinarySpatialOperatorDecoder {
    
    private BinarySpatialOperatorDecoder() {

    }

    public static boolean canDecode(final XMLStreamReader in) {
        return SpatialOperatorName.valueOf(in.getName()) != null;
    }

    public static BinarySpatialOperator decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final SpatialOperatorName spatialOperatorName = SpatialOperatorName.valueOf(in.getName());
        final List<ValueReference> valueReferences = new ArrayList<>(2);
        final GmlDecoderContext gmlContext = new GmlDecoderContext();
        gmlContext.getSrsDeque().push(SpatialReferenceSystem.CRS84);
        Envelope envelope = null;

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                if(ValueReferenceDecoder.canDecode(in)) {
                    valueReferences.add(ValueReferenceDecoder.decode(in, owsContext));
                } else if (EnvelopeDecoder.canDecode(in)) {
                    envelope = EnvelopeDecoder.decode(in, owsContext, gmlContext);
                }
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT && spatialOperatorName.toString().equals(in.getLocalName())) {
                break;
            }
        }

        ValueReference operand1 = null;
        SpatialDescription operand2 = null;

        if(valueReferences.size() > 1 && valueReferences.get(2) != null) {
            operand2 = new SpatialDescription(valueReferences.get(2));
        } else if(envelope != null) {
            operand2 = new SpatialDescription(envelope);
        }

        if(spatialOperatorName == SpatialOperatorName.BBOX && envelope == null) {
            throw new XMLStreamException("BinarySpatialOperator BBOX requires an Envelope be supplied, but none was found.", in.getLocation());
        } else if(spatialOperatorName != SpatialOperatorName.BBOX) {
            if(operand1 == null || operand2 == null) {
                throw new XMLStreamException(spatialOperatorName.toString() + " requires two parameters, but at least one is missing.", in.getLocation());
            }
        }

        return new BinarySpatialOperator(operand1, spatialOperatorName, operand2);
    }

}
