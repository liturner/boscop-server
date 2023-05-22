package de.turnertech.thw.cop.model.area;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.ows.api.Model;
import de.turnertech.thw.cop.ows.filter.OgcFilter;
import de.turnertech.thw.cop.util.BoundingBoxFilter;

public class AreaModel implements Model, BoundingBoxFilter {
    
    public static final AreaModel INSTANCE = new AreaModel();

    public static final String NAME = "Area";

    public static final String TYPENAME = "boscop:" + NAME;

    private static final List<Feature> areas = new LinkedList<>();

    private AreaModel() {

    }

    @Override
    public List<Feature> filter(BoundingBox boundingBox) {
        List<Feature> returnItems = new LinkedList<>();
        for(Feature area : areas) {
            if(boundingBox.contains(area)) {
                returnItems.add(area);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<Feature> filter(OgcFilter ogcFilter) {
        List<Feature> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(Feature area : areas) {
                if(area.getId().equals(featureId)) {
                    returnCollection.add(area);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<Feature> getAll() {
        return Collections.unmodifiableList(areas);
    }

    public boolean removeAll(Collection<Feature> areas) {
        return AreaModel.areas.removeAll(areas);
    }

    @Override
    public boolean addAll(Collection<Feature> dataObjects) {
        return areas.addAll(dataObjects);
    }

    @Override
    public boolean add(Feature newArea) {
        return areas.add(newArea);
    }

}
