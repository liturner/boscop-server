package de.turnertech.ows.gml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.turnertech.ows.srs.SpatialReferenceSystem;

public class FeatureType {
    
    /**
     * The "type name". This is the parameter which will be used in requests usw.
     */
    private final String name;

    private final String namespace;

    private final Map<String, FeatureProperty> featureTypeProperties;

    private SpatialReferenceSystem srs;

    private String title;

    private String description;

    public FeatureType(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
        this.featureTypeProperties = new HashMap<>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the srs
     */
    public SpatialReferenceSystem getSrs() {
        return srs;
    }

    /**
     * @param srs the srs to set
     */
    public void setSrs(SpatialReferenceSystem srs) {
        this.srs = srs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Feature createInstance() {
        return new Feature(this);
    }

    public FeatureProperty getIdProperty() {
        for(FeatureProperty property : featureTypeProperties.values()) {
            if(property.getPropertyType() == FeaturePropertyType.ID) return property;
        }
        return null;
    }

    public List<FeatureProperty> getBoundingBoxProperties() {
        List<FeatureProperty> returnList = new ArrayList<>(1);
        for(FeatureProperty property : featureTypeProperties.values()) {
            if(property.getPropertyType().isBoundingBoxProvider()) {
                returnList.add(property);
            }
        }
        return returnList;
    }

    public FeatureProperty getProperty(String propertyName) {
        return featureTypeProperties.get(propertyName);
    }

    public Collection<FeatureProperty> getProperties() {
        return featureTypeProperties.values();
    }

    public boolean hasProperty(String propertyName) {
        return featureTypeProperties.containsKey(propertyName);
    }

    public FeatureProperty putProperty(FeatureProperty value) {
        return featureTypeProperties.put(value.getName(), value);
    } 

}
