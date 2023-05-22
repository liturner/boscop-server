package de.turnertech.thw.cop.ows.api;

import java.util.Collection;

import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.ows.filter.OgcFilter;
import de.turnertech.thw.cop.util.BoundingBoxFilter;

public interface Model extends BoundingBoxFilter, BoundingBoxProvider {

    // TODO: This is incorect. It is reasonable that one model has multiple types.
    public FeatureType getFeatureType();

    public Collection<Feature> getAll();

    public Collection<Feature> filter(OgcFilter ogcFilter);

    public boolean add(Feature dataObject);

    public boolean addAll(Collection<Feature> dataObjects);

    public boolean removeAll(Collection<Feature> dataObjects);
    
}
