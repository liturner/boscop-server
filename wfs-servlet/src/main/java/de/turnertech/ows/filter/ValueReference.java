package de.turnertech.ows.filter;

import de.turnertech.ows.gml.IFeature;

public class ValueReference implements Expression {

    private final String reference;

    public ValueReference(final String reference) {
        this.reference = reference;
    }

    @Override
    public Object apply(IFeature feature) {
        return feature.getPropertyValue(reference);
    }

    @Override
    public String toString() {
        return reference;
    }
    
}
