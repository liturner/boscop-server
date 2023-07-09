package de.turnertech.ows.gml;

import java.util.Optional;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemConverter;

class DirectPositionDecoder implements XmlDecoder<DirectPosition> {
    
    public static final DirectPositionDecoder I = new DirectPositionDecoder();

    private DirectPositionDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return DirectPosition.QNAME.equals(in.getName()) ||
            DirectPosition.POS_QNAME.equals(in.getName()) ||
            DirectPosition.UPPER_CORNER_QNAME.equals(in.getName()) ||
            DirectPosition.LOWER_CORNER_QNAME.equals(in.getName());
    }

    @Override
    public DirectPosition decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        SpatialReferenceSystem srs = null;
        String srsName = in.getAttributeValue(OwsContext.GML_URI, "srsName");
        if(srsName == null) {
            srsName = in.getAttributeValue(null, "srsName");
        }
        if(srsName != null) {
            srs = SpatialReferenceSystem.from(srsName);
            if(srs != null) {
                owsContext.getGmlDecoderContext().getSrsDeque().push(srs);
            }
        }

        String posListString = in.getElementText();
        String[] coordValues = posListString.split(" ");
        
        DirectPosition recievedPosition = new DirectPosition(owsContext.getGmlDecoderContext().getSrsDeque().peek(), Double.parseDouble(coordValues[owsContext.getGmlDecoderContext().getSrsDeque().peek().getXIndex()]), Double.parseDouble(coordValues[owsContext.getGmlDecoderContext().getSrsDeque().peek().getYIndex()]));

        if(owsContext.getGmlDecoderContext().getFeatureType() != null && owsContext.getGmlDecoderContext().getFeatureType().getSrs() != null) {
            Optional<DirectPosition> posInFeatureSrs = SpatialReferenceSystemConverter.convertDirectPosition(recievedPosition, owsContext.getGmlDecoderContext().getFeatureType().getSrs());
            if(posInFeatureSrs.isPresent()) {
                recievedPosition = posInFeatureSrs.get();
            }
        }

        if(srs != null) {
            owsContext.getGmlDecoderContext().getSrsDeque().pop();
        }

        return recievedPosition;
    }

}
