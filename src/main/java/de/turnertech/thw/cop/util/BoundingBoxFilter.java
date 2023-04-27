package de.turnertech.thw.cop.util;

import java.util.List;

public interface BoundingBoxFilter {
    
    public List<? extends DataObject> filter(BoundingBox boundingBox);

}
