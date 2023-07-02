package de.turnertech.ows.filter;

import javax.xml.namespace.QName;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.gml.IFeature;

public class ValueReference implements Expression {

    public static final QName QNAME = new QName(OwsContext.FES_URI, "ValueReference");

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
