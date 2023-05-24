package de.turnertech.thw.cop.gml;

import org.w3c.dom.Node;

public class DirectPositionListDecoder implements GmlDecoder<DirectPositionList> {

    @Override
    public DirectPositionList decode(Node root, GmlDecoderContext context) {        
        Node srsNode = root.getAttributes().getNamedItem("srsName");
        if(srsNode != null) {
            // TODO: Actually Decode this and set it
        }

        String posListString = root.getTextContent();
        String[] coordValues = posListString.split(" ");
        
        DirectPositionList coordsOut = new DirectPositionList(coordValues.length / 2);
        for(int j = 0; j < coordValues.length; j += 2) {
            // Assumes Ordering! Make this based on the current CRS
            DirectPosition coord = new DirectPosition(Double.parseDouble(coordValues[j+1]), Double.parseDouble(coordValues[j]));
            coordsOut.add(coord);
        }

        return coordsOut;
    }
}

