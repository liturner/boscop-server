package de.turnertech.ows.filter;

import java.util.function.Supplier;

import de.turnertech.ows.gml.IFeature;

public class Literal implements Expression, Supplier<Object> {

    private final Object value;

    public Literal (final Object value) {
        this.value = value;
    }

    @Override
    public Object apply(IFeature feature) {
        return value;
    }

    @Override
    public Object get() {
        return this.apply(null);
    }

}
