package de.turnertech.thw.cop.ows.api;

import java.util.HashMap;

import de.turnertech.thw.cop.gml.FeatureType;

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
