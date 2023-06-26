package de.turnertech.ows.filter;

import java.util.List;

import de.turnertech.ows.gml.Envelope;
import de.turnertech.ows.gml.IFeature;

/**
 * @deprecated Use OgcFilter instead
 */
@Deprecated
public interface BoundingBoxFilter {
    
    public List<IFeature> filter(Envelope boundingBox);

}
