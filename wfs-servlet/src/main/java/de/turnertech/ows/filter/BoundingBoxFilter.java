package de.turnertech.ows.filter;

import java.util.List;

import de.turnertech.ows.gml.BoundingBox;
import de.turnertech.ows.gml.IFeature;

/**
 * @deprecated Use OgcFilter instead
 */
@Deprecated
public interface BoundingBoxFilter {
    
    public List<IFeature> filter(BoundingBox boundingBox);

}
