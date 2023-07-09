package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class EnvelopeDecoder implements XmlDecoder<Envelope> {

    public static final EnvelopeDecoder I = new EnvelopeDecoder();

    private EnvelopeDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        return Envelope.QNAME.equals(in.getName());
    }

    @Override
    public Envelope decode(DepthXMLStreamReader in, OwsContext owsContext) throws XMLStreamException {
        DirectPosition lowerCorner = null;
        DirectPosition upperCorner = null;

        String srsName = in.getAttributeValue(OwsContext.GML_URI, "srsName");
        if(srsName == null) {
            srsName = in.getAttributeValue(null, "srsName");
        }

        if(srsName != null) {
            final SpatialReferenceSystem srs = SpatialReferenceSystem.from(srsName);
            if(srs != null) {
                owsContext.getGmlDecoderContext().getSrsDeque().push(srs);
            }
        }

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                if(DirectPosition.LOWER_CORNER_QNAME.equals(in.getName())) {
                    lowerCorner = DirectPositionDecoder.I.decode(in, owsContext);
                } else if(DirectPosition.UPPER_CORNER_QNAME.equals(in.getName())) {
                    upperCorner = DirectPositionDecoder.I.decode(in, owsContext);
                } 
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT && Envelope.QNAME.equals(in.getName())) {
                break;
            }
        }

        return new Envelope(lowerCorner, upperCorner);
    }

}
