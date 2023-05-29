package de.turnertech.ows.gml;

import org.w3c.dom.Node;

public class DirectPositionDecoder implements GmlDecoder<DirectPosition> {
    
    @Override
    public DirectPosition decode(Node root, GmlDecoderContext context) {        
        Node srsNode = root.getAttributes().getNamedItem("srsName");
        if(srsNode != null) {
            // TODO: Actually Decode this and set it
        }

        String posListString = root.getTextContent();
        String[] coordValues = posListString.split(" ");
        
        return new DirectPosition(Double.parseDouble(coordValues[1]), Double.parseDouble(coordValues[0]));
    }

}
