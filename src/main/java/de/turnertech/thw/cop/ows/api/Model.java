package de.turnertech.thw.cop.ows.api;

import java.util.Collection;

import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.gml.IFeature;
import de.turnertech.thw.cop.ows.filter.OgcFilter;
import de.turnertech.thw.cop.util.BoundingBoxFilter;

public interface Model extends BoundingBoxFilter, BoundingBoxProvider {

    // TODO: This is incorect. It is reasonable that one model has multiple types.
    public FeatureType getFeatureType();

    public Collection<IFeature> getAll();

    public Collection<IFeature> filter(OgcFilter ogcFilter);

    public boolean add(IFeature dataObject);

    public boolean addAll(Collection<IFeature> dataObjects);

    public boolean removeAll(Collection<IFeature> dataObjects);
    
}
