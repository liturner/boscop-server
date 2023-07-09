package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;

class LineStringDecoder implements XmlDecoder<LineString> {

    public static final LineStringDecoder I = new LineStringDecoder();

    private LineStringDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return LineString.QNAME.equals(in.getName());
    }

    @Override
    public LineString decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final LineString returnElement = new LineString();
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
                if(DirectPositionListDecoder.I.canDecode(in)) {
                    returnElement.setPosList(DirectPositionListDecoder.I.decode(in, owsContext));
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
