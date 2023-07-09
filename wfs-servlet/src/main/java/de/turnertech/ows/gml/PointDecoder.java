package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class PointDecoder implements XmlDecoder<Point> {

    public static final PointDecoder I = new PointDecoder();

    private PointDecoder() {}
    
    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return Point.QNAME.equals(in.getName());
    }

    @Override
    public Point decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final Point returnElement = new Point();
        final int myDepth = in.getDepth() - 1;
        SpatialReferenceSystem srs = null;
        String srsName = in.getAttributeValue(OwsContext.GML_URI, "srsName");
        if(srsName == null) {
            srsName = in.getAttributeValue(null, "srsName");
        }
        if(srsName != null) {
            srs = SpatialReferenceSystem.from(srsName);
            if(srs != null) {
                owsContext.getGmlDecoderContext().getSrsDeque().push(srs);
            }
        }

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                if(DirectPositionDecoder.I.canDecode(in)) {
                    returnElement.setPos(DirectPositionDecoder.I.decode(in, owsContext));
                }
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT && in.getDepth() <= myDepth) {
                break;
            }
        }

        if(srs != null) {
            owsContext.getGmlDecoderContext().getSrsDeque().pop();
        }

        return returnElement;
    }

}
