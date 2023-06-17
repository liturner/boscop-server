package de.turnertech.ows.gml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.turnertech.ows.srs.SpatialReferenceSystem;

public class PolygonDecoder implements GmlDecoder<Polygon> {

    @Override
    public Polygon decode(Node root, GmlDecoderContext context) {
        Polygon returnPolygon = new Polygon();
        
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
            if("exterior".equals(child.getNodeName())) {
                LinearRing exterior = new LinearRingDecoder().decode(child.getFirstChild(), context);
                returnPolygon.setExterior(exterior);
            }
        }

        if(srs != null) {
            context.getSrsDeque().pop();
        }

        return returnPolygon;
    }
    
}
