package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;

class GeometryDecoder implements XmlDecoder<GmlElement> {
    
    public static final GeometryDecoder I = new GeometryDecoder();

    private GeometryDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        return PointDecoder.I.canDecode(in) ||
                PolygonDecoder.I.canDecode(in) ||
                LineStringDecoder.I.canDecode(in);
    }

    @Override
    public GmlElement decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        GmlElement returnElement = null;
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
                if(PointDecoder.I.canDecode(in)) {
                    returnElement = PointDecoder.I.decode(in, owsContext);
                } else if(PolygonDecoder.I.canDecode(in)) {
                    returnElement = PolygonDecoder.I.decode(in, owsContext);
                } else if(LineStringDecoder.I.canDecode(in)) {
                    returnElement = LineStringDecoder.I.decode(in, owsContext);
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
