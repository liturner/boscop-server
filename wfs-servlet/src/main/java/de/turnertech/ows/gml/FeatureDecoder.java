package de.turnertech.ows.gml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.turnertech.ows.Logging;

public class FeatureDecoder {

    public static IFeature decode(Node xmlRootNode, GmlDecoderContext gmlContext, FeatureType featureType) {
        Feature returnFeature = featureType.createInstance();
        xmlRootNode.normalize();

        NodeList propertyNodes = xmlRootNode.getChildNodes();
        for(int i = 0; i < propertyNodes.getLength(); ++i) {
            Node propertyNode = propertyNodes.item(i);
            if(propertyNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            String propertyName = propertyNode.getNodeName();
            String propertyNamespace = propertyNode.getNamespaceURI();

            //TODO: Here we need to rely on the FeatureType, and its map of FeatureProperty instances, which will
            //contain type information needed for handling casting to the correct data type.
            if(!featureType.hasProperty(propertyName)) {
                // TODO: Throw Exception
                return null;
            }
            
            FeatureProperty featureProperty = featureType.getProperty(propertyName);
            if(featureProperty.getPropertyType() == FeaturePropertyType.TEXT) {
                returnFeature.setPropertyValue(propertyName, propertyNode.getTextContent());
            } else if(featureProperty.getPropertyType() == FeaturePropertyType.POLYGON) {
                Element element = (Element)propertyNode;
                returnFeature.setPropertyValue(propertyName, new PolygonDecoder().decode(element.getElementsByTagName("Polygon").item(0), gmlContext));
            } else if(featureProperty.getPropertyType() == FeaturePropertyType.POINT) {
                Element element = (Element)propertyNode;
                returnFeature.setPropertyValue(propertyName, new PointDecoder().decode(element.getElementsByTagName("Point").item(0), gmlContext));
            } else if(featureProperty.getPropertyType() == FeaturePropertyType.ID) {
                returnFeature.setPropertyValue(propertyName, propertyNode.getTextContent());
            } else {
                Logging.LOG.severe("FeatureDecoder: Property was not decoded - " + propertyName);
            }
        }

        gmlContext.getFeatureIdRetriever().retrieveFeatureId(returnFeature);

        return returnFeature;
    }
    
}
