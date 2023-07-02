package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class EnvelopeDecoder {

    private EnvelopeDecoder() {

    }

    public static boolean canDecode(final XMLStreamReader in) {
        return Envelope.QNAME.equals(in.getName());
    }

    public static Envelope decode(final XMLStreamReader in, final OwsContext owsContext, final GmlDecoderContext gmlContext) throws XMLStreamException {
        DirectPosition lowerCorner = null;
        DirectPosition upperCorner = null;

        String srsName = in.getAttributeValue(OwsContext.GML_URI, "srsName");
        if(srsName == null) {
            srsName = in.getAttributeValue(null, "srsName");
        }

        if(srsName != null) {
            final SpatialReferenceSystem srs = SpatialReferenceSystem.from(srsName);
            if(srs != null) {
                gmlContext.getSrsDeque().push(srs);
            }
        }

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                if(DirectPosition.LOWER_CORNER_QNAME.equals(in.getName())) {
                    lowerCorner = DirectPositionDecoder.decode(in, owsContext, gmlContext);
                } else if(DirectPosition.UPPER_CORNER_QNAME.equals(in.getName())) {
                    upperCorner = DirectPositionDecoder.decode(in, owsContext, gmlContext);
                } 
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT && Envelope.QNAME.equals(in.getName())) {
                break;
            }
        }

        return new Envelope(lowerCorner, upperCorner);
    }

}
