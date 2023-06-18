package de.turnertech.ows.gml;

import org.w3c.dom.Node;

import de.turnertech.ows.srs.SpatialReferenceSystem;

public class GeometryDecoder implements GmlDecoder<GmlElement> {
    
    @Override
    public GmlElement decode(Node root, GmlDecoderContext context) {
        GmlElement returnElement = null;
        
        Node srsNode = root.getAttributes().getNamedItem("srsName");
        SpatialReferenceSystem srs = null;
        if(srsNode != null) {
            srs = SpatialReferenceSystem.from(srsNode.getNodeValue());
            if(srs != null) {
                context.getSrsDeque().push(srs);
            }
        }

        if("Point".equals(root.getNodeName())) {
            return new PointDecoder().decode(root, context);
        } else if("Polygon".equals(root.getNodeName())) {
            return new PolygonDecoder().decode(root, context);
        } else if("LineString".equals(root.getNodeName())) {
            return new LineStringDecoder().decode(root, context);
        }

        if(srs != null) {
            context.getSrsDeque().pop();
        }

        return returnElement;
    }

}
