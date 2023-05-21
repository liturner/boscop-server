package de.turnertech.thw.cop.ows.model.area;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.ows.filter.OgcFilter;
import de.turnertech.thw.cop.util.BoundingBox;
import de.turnertech.thw.cop.util.BoundingBoxFilter;
import de.turnertech.thw.cop.util.DataObject;
import de.turnertech.thw.cop.util.Model;

public class AreaModel implements Model, BoundingBoxFilter {
    
    public static final AreaModel INSTANCE = new AreaModel();

    public static final String NAME = "Area";

    public static final String TYPENAME = "boscop:" + NAME;

    private static final List<DataObject> areas = new LinkedList<>();

    private AreaModel() {

    }

    @Override
    public List<DataObject> filter(BoundingBox boundingBox) {
        List<DataObject> returnItems = new LinkedList<>();
        for(DataObject area : areas) {
            if(boundingBox.contains(area)) {
                returnItems.add(area);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<DataObject> filter(OgcFilter ogcFilter) {
        List<DataObject> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(DataObject area : areas) {
                if(area.getId().equals(featureId)) {
                    returnCollection.add(area);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<DataObject> getAll() {
        return Collections.unmodifiableList(areas);
    }

    public boolean removeAll(Collection<DataObject> areas) {
        return AreaModel.areas.removeAll(areas);
    }

    @Override
    public boolean addAll(Collection<DataObject> dataObjects) {
        return areas.addAll(dataObjects);
    }

    @Override
    public boolean add(DataObject newArea) {
        return areas.add(newArea);
    }

}
