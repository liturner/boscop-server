package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class LinearRingDecoder implements XmlDecoder<LinearRing> {

    public static final LinearRingDecoder I = new LinearRingDecoder();

    private LinearRingDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return LinearRing.QNAME.equals(in.getName());
    }

    @Override
    public LinearRing decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final LinearRing returnElement = new LinearRing();
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
