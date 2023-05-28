package de.turnertech.ows.filter;

import java.util.ArrayList;
import java.util.List;

public class OgcFilter {

    private final List<String> featureIdFilters;
    
    public OgcFilter() {
        featureIdFilters = new ArrayList<>(1);
    }

    public List<String> getFeatureIdFilters() {
        return featureIdFilters;
    }

}
