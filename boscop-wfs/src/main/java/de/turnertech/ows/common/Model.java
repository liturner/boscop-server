package de.turnertech.ows.common;

import java.util.Collection;

import de.turnertech.ows.filter.BoundingBoxFilter;
import de.turnertech.ows.filter.OgcFilter;
import de.turnertech.ows.gml.BoundingBoxProvider;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.IFeature;

public interface Model extends BoundingBoxFilter, BoundingBoxProvider {

    // TODO: This is incorect. It is reasonable that one model has multiple types.
    public FeatureType getFeatureType();

    public Collection<IFeature> getAll();

    public Collection<IFeature> filter(OgcFilter ogcFilter);

    public boolean add(IFeature dataObject);

    public boolean addAll(Collection<IFeature> dataObjects);

    public boolean removeAll(Collection<IFeature> dataObjects);
    
}
