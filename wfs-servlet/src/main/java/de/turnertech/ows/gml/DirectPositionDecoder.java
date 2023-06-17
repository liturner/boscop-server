package de.turnertech.ows.gml;

import java.util.Optional;

import org.w3c.dom.Node;

import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemConverter;

public class DirectPositionDecoder implements GmlDecoder<DirectPosition> {
    
    @Override
    public DirectPosition decode(Node root, GmlDecoderContext context) {        
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
        
        DirectPosition recievedPosition = new DirectPosition(context.getSrsDeque().peek(), Double.parseDouble(coordValues[context.getSrsDeque().peek().getXIndex()]), Double.parseDouble(coordValues[context.getSrsDeque().peek().getYIndex()]));
        Optional<DirectPosition> posInFeatureSrs = SpatialReferenceSystemConverter.convertDirectPosition(recievedPosition, context.getFeatureType().getSrs());

        if(srs != null) {
            context.getSrsDeque().pop();
        }

        return posInFeatureSrs.get();
    }

}
