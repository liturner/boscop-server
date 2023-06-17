package de.turnertech.ows.gml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.turnertech.ows.srs.SpatialReferenceSystem;

public class LinearRingDecoder implements GmlDecoder<LinearRing> {

    @Override
    public LinearRing decode(Node root, GmlDecoderContext context) {
        LinearRing returnElement = new LinearRing();
        
        Node srsNode = root.getAttributes().getNamedItem("srsName");
        SpatialReferenceSystem srs = null;
        if(srsNode != null) {
            srs = SpatialReferenceSystem.from(srsNode.getNodeValue());
            if(srs != null) {
                context.getSrsDeque().push(srs);
            }
        }

        NodeList children = root.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if(child.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if("posList".equals(child.getNodeName())) {
                DirectPositionList posList = new DirectPositionListDecoder().decode(child, context);
                returnElement.setPosList(posList);
            }
        }

        if(srs != null) {
            context.getSrsDeque().pop();
        }

        return returnElement;
    }
}
