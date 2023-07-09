package de.turnertech.ows.filter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.gml.Envelope;
import de.turnertech.ows.gml.EnvelopeDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class BinarySpatialOperatorDecoder implements XmlDecoder<BinarySpatialOperator> {
    
    static final BinarySpatialOperatorDecoder I = new BinarySpatialOperatorDecoder();

    private BinarySpatialOperatorDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return SpatialOperatorName.valueOf(in.getName()) != null;
    }

    @Override
    public BinarySpatialOperator decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final SpatialOperatorName spatialOperatorName = SpatialOperatorName.valueOf(in.getName());
        final List<ValueReference> valueReferences = new ArrayList<>(2);
        owsContext.getGmlDecoderContext().getSrsDeque().push(SpatialReferenceSystem.CRS84);
        Envelope envelope = null;

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                if(ValueReferenceDecoder.I.canDecode(in)) {
                    valueReferences.add(ValueReferenceDecoder.I.decode(in, owsContext));
                } else if (EnvelopeDecoder.I.canDecode(in)) {
                    envelope = EnvelopeDecoder.I.decode(in, owsContext);
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
