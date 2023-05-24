package de.turnertech.thw.cop.gml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamWriter;

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
    public BoundingBox getBoundingBox() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoundingBox'");
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI,
            SpatialReferenceSystemRepresentation srs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeGml'");
    }

    @Override
    public String getGmlName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGmlName'");
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public FeatureType getFeatureType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFeatureType'");
    }
    
}
