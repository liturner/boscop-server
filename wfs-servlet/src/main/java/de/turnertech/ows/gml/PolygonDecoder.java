package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class PolygonDecoder implements XmlDecoder<Polygon> {

    public static final PolygonDecoder I = new PolygonDecoder();

    private PolygonDecoder() {}

    @Override
    public boolean canDecode(final DepthXMLStreamReader in) {
        return Polygon.QNAME.equals(in.getName());
    }

    @Override
    public Polygon decode(final DepthXMLStreamReader in, final OwsContext owsContext) throws XMLStreamException {
        final Polygon returnElement = new Polygon();
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
                if("exterior".equals(in.getLocalName())) {
                    returnElement.setExterior(LinearRingDecoder.I.decode(in, owsContext));
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
