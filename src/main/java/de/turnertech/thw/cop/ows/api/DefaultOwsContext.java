package de.turnertech.thw.cop.ows.api;

import java.util.Collection;

import de.turnertech.thw.cop.ows.parameter.WfsVersionValue;

public class DefaultOwsContext implements OwsContext {
    
    ModelProvider modelProvider;

    Collection<WfsVersionValue> supportedWfsVersions;

    @Override
    public ModelProvider getModelProvider() {
        return modelProvider;
    }

    void setModelProvider(ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
    }

    @Override
    public Collection<WfsVersionValue> getSupportedWfsVersions() {
        return supportedWfsVersions;
    }

    void setSupportedWfsVersions(Collection<WfsVersionValue> supportedWfsVersions) {
        this.supportedWfsVersions = supportedWfsVersions;
    }

}
