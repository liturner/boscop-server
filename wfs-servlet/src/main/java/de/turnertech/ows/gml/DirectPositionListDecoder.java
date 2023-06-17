package de.turnertech.ows.gml;

import java.util.Optional;

import org.w3c.dom.Node;

import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemConverter;

public class DirectPositionListDecoder implements GmlDecoder<DirectPositionList> {

    @Override
    public DirectPositionList decode(Node root, GmlDecoderContext context) {        
        Node srsNode = root.getAttributes().getNamedItem("srsName");
        SpatialReferenceSystem srs = null;
        if(srsNode != null) {
            srs = SpatialReferenceSystem.from(srsNode.getNodeValue());
            if(srs != null) {
                context.getSrsDeque().push(srs);
            }
        }

        String posListString = root.getTextContent();
        String[] coordValues = posListString.split(" ");
        
        DirectPositionList coordsOut = new DirectPositionList(context.getFeatureType().getSrs(), coordValues.length / 2);
        for(int j = 0; j < coordValues.length; j += 2) {
            DirectPosition recievedPosition = new DirectPosition(context.getSrsDeque().peek(), Double.parseDouble(coordValues[j + context.getSrsDeque().peek().getXIndex()]), Double.parseDouble(coordValues[j + context.getSrsDeque().peek().getYIndex()]));
            Optional<DirectPosition> posInFeatureSrs = SpatialReferenceSystemConverter.convertDirectPosition(recievedPosition, context.getFeatureType().getSrs());
            coordsOut.add(posInFeatureSrs.get());
        }

        if(srs != null) {
            context.getSrsDeque().pop();
        }

        return coordsOut;
    }
}

