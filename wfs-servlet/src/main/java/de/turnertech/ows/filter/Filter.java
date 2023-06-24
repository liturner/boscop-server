package de.turnertech.ows.filter;

import java.util.function.Predicate;

import de.turnertech.ows.gml.IFeature;

public class Filter implements Predicate<IFeature> {
    
    private final Operator filter;

    public Filter(final Operator filter) {
        this.filter = filter;
    }

    /**
     * @return the filter
     */
    public Operator getFilter() {
        return filter;
    }

    @Override
    public boolean test(IFeature feature) {
        return filter.test(feature);
    }

}
