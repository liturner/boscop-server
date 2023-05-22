package de.turnertech.thw.cop.ows.api;

import java.util.Collection;

import de.turnertech.thw.cop.ows.parameter.WfsVersionValue;

public interface OwsContext {

    public ModelProvider getModelProvider();

    public Collection<WfsVersionValue> getSupportedWfsVersions();

}