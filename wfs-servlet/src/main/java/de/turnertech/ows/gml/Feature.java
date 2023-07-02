package de.turnertech.ows.gml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

public class Feature implements IFeature {

    private final Map<String, Object> fields;

    private final FeatureType featureType;

    Feature(FeatureType featureType) {
        fields = new HashMap<>();
        this.featureType = featureType;
    }

    public Object getPropertyValue(String propertyName) {
        return fields.get(propertyName);
    }

    public boolean hasPropertyValue(String propertyName) {
        return fields.containsKey(propertyName);
    }

    public Object setPropertyValue(String propertyName, Object value) {
        return fields.put(propertyName, value);
    }

    @Override
    public Envelope getBoundingBox() {
        List<FeatureProperty> bboxProperties = featureType.getBoundingBoxProperties();
        if(bboxProperties.size() == 0) {
            return null;
        }
        Envelope returnBox = new Envelope();
        for (FeatureProperty property : bboxProperties) {
            BoundingBoxProvider bboxProvider =  (BoundingBoxProvider)fields.get(property.getName());
            if(bboxProvider != null) {
                returnBox.expandToFit(bboxProvider.getBoundingBox());
            }
        }
        return returnBox;
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);

            String id = getId();
            if(id != null && !"".equals(id)) {
                out.writeAttribute(OwsContext.GML_URI, "id", id);
            }

            for (Entry<String, Object> field : fields.entrySet()) {
                FeatureProperty property = featureType.getProperty(field.getKey());
                FeaturePropertyType propertyType = property.getPropertyType();

                if(propertyType == FeaturePropertyType.TEXT) {
                    out.writeStartElement(namespaceURI, field.getKey());
                    out.writeCharacters(field.getValue().toString());
                    out.writeEndElement();
                } else if(
                        propertyType == FeaturePropertyType.POLYGON || 
                        propertyType == FeaturePropertyType.POINT || 
                        propertyType == FeaturePropertyType.LINE_STRING || 
                        propertyType == FeaturePropertyType.GEOMETRY) {
                    GmlElement geom = (GmlElement)field.getValue();
                    out.writeStartElement(namespaceURI, field.getKey());
                    geom.writeGml(out, geom.getGmlName(), GmlElement.NAMESPACE, srs);
                    out.writeEndElement();
                } else if(propertyType == FeaturePropertyType.ID) {
                    // Do Nothing, special field
                } else {
                    out.writeEmptyElement(namespaceURI, field.getKey());
                    Logging.LOG.severe("Feature: parameter not written as property type not implemented: " + propertyType);
                }
            }
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for Feature");
        }
    }

    @Override
    public String getGmlName() {
        return featureType.getName();
    }

    @Override
    public String getId() {
        FeatureProperty idProperty = featureType.getIdProperty();
        if(idProperty == null) return null;
        return Objects.toString(getPropertyValue(idProperty.getName()), null);
    }

    @Override
    public FeatureType getFeatureType() {
        return featureType;
    }
    
}
