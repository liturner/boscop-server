package de.turnertech.thw.cop.util;

import java.util.Collection;

import de.turnertech.thw.cop.ows.filter.OgcFilter;

public interface Model {

    public Collection<DataObject> getAll();

    public Collection<DataObject> filter(OgcFilter ogcFilter);

    public boolean add(DataObject dataObject);

    public boolean addAll(Collection<DataObject> dataObjects);

    public boolean removeAll(Collection<DataObject> dataObjects);
    
}
