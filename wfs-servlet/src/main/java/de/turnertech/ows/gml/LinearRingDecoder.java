package de.turnertech.ows.gml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LinearRingDecoder implements GmlDecoder<LinearRing> {

    @Override
    public LinearRing decode(Node root, GmlDecoderContext context) {
        LinearRing returnElement = new LinearRing();
        
        Node srsNode = root.getAttributes().getNamedItem("srsName");
        if(srsNode != null) {
            // TODO: Actually Decode this and set it
        }

        NodeList children = root.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if(child.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if("posList".equals(child.getNodeName())) {
                // TODO: Defer to a "posListDecoder"
                DirectPositionList posList = new DirectPositionListDecoder().decode(child, context);
                returnElement.setPosList(posList);
            }
        }


        return returnElement;
    }
}
