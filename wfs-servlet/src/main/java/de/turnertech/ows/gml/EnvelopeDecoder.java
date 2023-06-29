package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.turnertech.ows.common.OwsContext;

public class EnvelopeDecoder {

    private EnvelopeDecoder() {

    }

    public static boolean canDecode(final XMLStreamReader in) {
        return Envelope.QNAME.equals(in.getName());
    }

    public static Envelope decode(final XMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                if(ValueReferenceDecoder.canDecode(in)) {
                    valueReferences.add(ValueReferenceDecoder.decode(in, owsContext));
                } 
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT && spatialOperatorName.toString().equals(in.getLocalName())) {
                break;
            }
        }

        return new Envelope();
    }

}
