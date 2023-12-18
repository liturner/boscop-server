package de.turnertech.ows.common;

import de.turnertech.ows.filter.BoundingBoxFilter;
import de.turnertech.ows.filter.OgcFilter;
import de.turnertech.ows.gml.BoundingBoxProvider;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.IFeature;
import java.io.File;
import java.util.Collection;

public interface Model extends BoundingBoxFilter, BoundingBoxProvider {

    // TODO: This is incorect. It is reasonable that one model has multiple types.
    public FeatureType getFeatureType();

    public Collection<IFeature> getAll();

    public Collection<IFeature> filter(OgcFilter ogcFilter);

    public boolean add(IFeature dataObject);

    public boolean addAll(Collection<IFeature> dataObjects);

    public boolean removeAll(Collection<IFeature> dataObjects);

    /**
     * @deprecated Use load and persist instead. We want to keep the callback
     * logic simple.
     * Returns an absolute path to the file storag for this {@link Model}
     * @return an absolute path to the file storag for this {@link Model}
     */
    @Deprecated
    public File getStorageLocation();

    /**
     * Callback to instruct the model to persist itself.
     */
    public void persist();

    /**
     * Callback to instruct the model to persist itself.
     */
    public void load();

}
