package de.turnertech.ows.gml;

import java.util.Optional;

import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemConverter;

class DirectPositionListDecoder implements XmlDecoder<DirectPositionList> {

    public static final DirectPositionListDecoder I = new DirectPositionListDecoder();

    private DirectPositionListDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return DirectPositionList.QNAME.equals(in.getName());
    }

    @Override
    public DirectPositionList decode(DepthXMLStreamReader in, OwsContext owsContext) throws XMLStreamException {
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

        DirectPositionList coordsOut = new DirectPositionList(owsContext.getGmlDecoderContext().getFeatureType().getSrs(), coordValues.length / 2);
        for(int j = 0; j < coordValues.length; j += 2) {
            DirectPosition recievedPosition = new DirectPosition(owsContext.getGmlDecoderContext().getSrsDeque().peek(), Double.parseDouble(coordValues[j + owsContext.getGmlDecoderContext().getSrsDeque().peek().getXIndex()]), Double.parseDouble(coordValues[j + owsContext.getGmlDecoderContext().getSrsDeque().peek().getYIndex()]));
            Optional<DirectPosition> posInFeatureSrs = SpatialReferenceSystemConverter.convertDirectPosition(recievedPosition, owsContext.getGmlDecoderContext().getFeatureType().getSrs());
            coordsOut.add(posInFeatureSrs.get());
        }

        if(srs != null) {
            owsContext.getGmlDecoderContext().getSrsDeque().pop();
        }

        return coordsOut;
    }

}
