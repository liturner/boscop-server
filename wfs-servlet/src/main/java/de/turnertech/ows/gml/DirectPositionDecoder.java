package de.turnertech.ows.gml;

import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Node;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemConverter;

public class DirectPositionDecoder implements GmlDecoder<DirectPosition> {
    
    public static boolean canDecode(final XMLStreamReader in) {
        return DirectPosition.QNAME.equals(in.getName()) ||
            DirectPosition.POS_QNAME.equals(in.getName()) ||
            DirectPosition.UPPER_CORNER_QNAME.equals(in.getName()) ||
            DirectPosition.LOWER_CORNER_QNAME.equals(in.getName());
    }

    public static DirectPosition decode(final XMLStreamReader in, final OwsContext owsContext, final GmlDecoderContext gmlContext) throws XMLStreamException {
        SpatialReferenceSystem srs = null;
        String srsName = in.getAttributeValue(OwsContext.GML_URI, "srsName");
        if(srsName == null) {
            srsName = in.getAttributeValue(null, "srsName");
        }
        if(srsName != null) {
            srs = SpatialReferenceSystem.from(srsName);
            if(srs != null) {
                gmlContext.getSrsDeque().push(srs);
            }
        }

        String posListString = in.getElementText();
        String[] coordValues = posListString.split(" ");
        
        DirectPosition recievedPosition = new DirectPosition(gmlContext.getSrsDeque().peek(), Double.parseDouble(coordValues[gmlContext.getSrsDeque().peek().getXIndex()]), Double.parseDouble(coordValues[gmlContext.getSrsDeque().peek().getYIndex()]));

        if(gmlContext.getFeatureType() != null && gmlContext.getFeatureType().getSrs() != null) {
            Optional<DirectPosition> posInFeatureSrs = SpatialReferenceSystemConverter.convertDirectPosition(recievedPosition, gmlContext.getFeatureType().getSrs());
            if(posInFeatureSrs.isPresent()) {
                recievedPosition = posInFeatureSrs.get();
            }
        }

        if(srs != null) {
            gmlContext.getSrsDeque().pop();
        }

        return recievedPosition;
    }

    @Deprecated
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
