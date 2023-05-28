package de.turnertech.ows.common;

import java.util.HashMap;

import de.turnertech.ows.gml.FeatureType;

public class DefaultModelProvider implements ModelProvider {

    private final HashMap<FeatureType, Model> models;

    public DefaultModelProvider() {
        models = new HashMap<>();
    }

    @Override
    public Model getModel(FeatureType typeName) {
        return models.get(typeName);
    }

    public Model putModel(FeatureType typeName, Model model) {
        return models.put(typeName, model);
    }
    
}
