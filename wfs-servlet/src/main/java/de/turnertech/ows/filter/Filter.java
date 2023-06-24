package de.turnertech.ows.filter;

import java.util.function.Predicate;

import de.turnertech.ows.gml.IFeature;

public class Filter implements Predicate<IFeature> {
    
    private Operator filter;

    /**
     * @return the filter
     */
    public Operator getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(Operator filter) {
        this.filter = filter;
    }

    @Override
    public boolean test(IFeature feature) {
        return filter.test(feature);
    }

}
