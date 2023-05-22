package de.turnertech.thw.cop.ows.api;

import java.util.Collection;

import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.ows.filter.OgcFilter;

public interface Model {

    public Collection<Feature> getAll();

    public Collection<Feature> filter(OgcFilter ogcFilter);

    public boolean add(Feature dataObject);

    public boolean addAll(Collection<Feature> dataObjects);

    public boolean removeAll(Collection<Feature> dataObjects);
    
}
