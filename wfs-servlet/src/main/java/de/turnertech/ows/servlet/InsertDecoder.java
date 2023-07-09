package de.turnertech.ows.servlet;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.gml.FeatureDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class InsertDecoder implements XmlDecoder<Insert> {

    public static final InsertDecoder I = new InsertDecoder();

    private InsertDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return Insert.QNAME.equals(in.getName());
    }

    @Override
    public Insert decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final int myDepth = in.getDepth() - 1;
        final Insert returnObject = new Insert();

        for(int i = 0; i < in.getAttributeCount(); ++i) {
            final QName attributeName = in.getAttributeName(i);
            if(attributeName.getLocalPart().equals("srsName")) {
                returnObject.setSrsName(SpatialReferenceSystem.from(in.getAttributeValue(i)));
                owsContext.getGmlDecoderContext().getSrsDeque().push(returnObject.getSrsName());
            }
        }

        while(in.hasNext()) {
            int xmlEvent = in.next();

            if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                if(FeatureDecoder.I.canDecode(in)) {
                    returnObject.getValue().add(FeatureDecoder.I.decode(in, owsContext));
                }
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT && in.getDepth() == myDepth) {
                break;
            }
        }

        if(returnObject.getSrsName() != null) {
            owsContext.getGmlDecoderContext().getSrsDeque().pop();
        }

        return returnObject;
    }
    
}
