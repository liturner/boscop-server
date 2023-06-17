package de.turnertech.ows.gml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.turnertech.ows.srs.SpatialReferenceSystem;

public class PointDecoder implements GmlDecoder<Point> {
    
    @Override
    public Point decode(Node root, GmlDecoderContext context) {
        Point returnElement = new Point();
        
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
            if("pos".equals(child.getNodeName())) {
                DirectPosition pos = new DirectPositionDecoder().decode(child, context);
                returnElement.setPos(pos);
            }
        }

        if(srs != null) {
            context.getSrsDeque().pop();
        }

        return returnElement;
    }

}
