package de.turnertech.ows.common;

import de.turnertech.ows.gml.FeatureType;

public interface ModelProvider {
    
    public Model getModel(FeatureType typeName);

}
