package de.turnertech.ows.gml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import de.turnertech.ows.Logging;
import de.turnertech.ows.common.DepthXMLStreamReader;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.XmlDecoder;

public class FeatureDecoder implements XmlDecoder<IFeature> {

    public static final FeatureDecoder I = new FeatureDecoder();

    private FeatureDecoder() {}

    @Override
    public boolean canDecode(DepthXMLStreamReader in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDecode'");
    }

    @Override
    public IFeature decode(DepthXMLStreamReader in, OwsContext owsContext) throws XMLStreamException {
        final int myDepth = in.getDepth() - 1;
        Feature returnFeature = owsContext.getGmlDecoderContext().getFeatureType().createInstance();
        owsContext.getGmlDecoderContext().getSrsDeque().push(owsContext.getGmlDecoderContext().getFeatureType().getSrs());

        while(in.hasNext()) {
            int xmlEvent = in.next();

            // Simple Features Profile. We only care about items one element below the feature node here.
            if (xmlEvent == XMLStreamConstants.START_ELEMENT && in.getDepth() == myDepth + 1) {
                if(!owsContext.getGmlDecoderContext().getFeatureType().hasProperty(in.getLocalName())) {
                    throw new XMLStreamException("Unknown property supplied in feature", in.getLocation());
                }

                FeatureProperty featureProperty = owsContext.getGmlDecoderContext().getFeatureType().getProperty(in.getLocalName());
                if(featureProperty.getPropertyType() == FeaturePropertyType.TEXT) {
                    returnFeature.setPropertyValue(in.getLocalName(), in.getElementText());
                } else if(featureProperty.getPropertyType() == FeaturePropertyType.POLYGON) {
                    returnFeature.setPropertyValue(in.getLocalName(), GeometryDecoder.I.decode(in, owsContext));
                } else if(featureProperty.getPropertyType() == FeaturePropertyType.POINT) {
                    returnFeature.setPropertyValue(in.getLocalName(), GeometryDecoder.I.decode(in, owsContext));
                } else if(featureProperty.getPropertyType() == FeaturePropertyType.LINE_STRING) {
                    returnFeature.setPropertyValue(in.getLocalName(), GeometryDecoder.I.decode(in, owsContext));
                } else if(featureProperty.getPropertyType() == FeaturePropertyType.ID) {
                    returnFeature.setPropertyValue(in.getLocalName(), in.getElementText());
                } else if(featureProperty.getPropertyType() == FeaturePropertyType.GEOMETRY) {
                    returnFeature.setPropertyValue(in.getLocalName(), GeometryDecoder.I.decode(in, owsContext));
                } else {
                    Logging.LOG.severe("FeatureDecoder: Property was not decoded - " + in.getLocalName());
                }
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT && in.getDepth() <= myDepth) {
                break;
            }
        }

        owsContext.getGmlDecoderContext().getFeatureIdRetriever().retrieveFeatureId(returnFeature);

        return returnFeature;
    }
    
}
