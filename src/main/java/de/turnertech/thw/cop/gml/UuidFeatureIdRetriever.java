package de.turnertech.thw.cop.gml;

import java.util.UUID;

/**
 * Defines the logic for unique ID generation. This implementation will generate a UUID
 * if no ID was already present, and will set it on the supplied object!
 * 
 * If you only want to check if the ID is present, use the .getId() function of the 
 * object.
 * 
 * This class will be of value in TransactionHandlers. There we will be handling new
 * instances of {@link IFeature} which may not have been sent with an ID parameter.
 */
public class UuidFeatureIdRetriever implements FeatureIdRetriever {
    
    @Override
    public String retrieveFeatureId(IFeature feature) {
        String id = feature.getId();

        if(id == null) {
            FeatureProperty idProperty = feature.getFeatureType().getIdProperty();
            if(idProperty != null) {
                id = UUID.randomUUID().toString();
                feature.setPropertyValue(idProperty.getName(), id);
            }
        }

        return id;
    }

}
