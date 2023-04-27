package de.turnertech.thw.cop.util;

import java.util.Collection;

public interface Model<T extends DataObject> {

    public Collection<T> getAll();

    public boolean add(T dataObject);

    public boolean addAll(Collection<T> dataObjects);
    
}
