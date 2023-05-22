package de.turnertech.thw.cop.ows.api;

import java.util.HashMap;

public class DefaultModelProvider implements ModelProvider {

    private final HashMap<String, Model> models;

    public DefaultModelProvider() {
        models = new HashMap<>();
    }

    @Override
    public Model getModel(String typeName) {
        return models.get(typeName);
    }

    public Model putModel(String typeName, Model model) {
        return models.put(typeName, model);
    }
    
}
