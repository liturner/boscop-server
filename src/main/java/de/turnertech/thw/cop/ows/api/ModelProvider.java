package de.turnertech.thw.cop.ows.api;

import de.turnertech.thw.cop.gml.FeatureType;

public interface ModelProvider {
    
    public Model getModel(FeatureType typeName);

}
