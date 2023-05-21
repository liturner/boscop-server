package de.turnertech.thw.cop.ows.model.hazard;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.ows.filter.OgcFilter;
import de.turnertech.thw.cop.util.BoundingBox;
import de.turnertech.thw.cop.util.BoundingBoxFilter;
import de.turnertech.thw.cop.util.DataObject;
import de.turnertech.thw.cop.util.Model;

public class HazardModel implements Model, BoundingBoxFilter {
    
    public static final HazardModel INSTANCE = new HazardModel();

    public static final String NAME = "Hazard";

    public static final String TYPENAME = "boscop:" + NAME;

    private static final List<DataObject> hazards = new LinkedList<>();

    private HazardModel() {

    }

    @Override
    public List<DataObject> filter(BoundingBox boundingBox) {
        List<DataObject> returnItems = new LinkedList<>();
        for(DataObject hazard : hazards) {
            if(boundingBox.contains(hazard)) {
                returnItems.add(hazard);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<DataObject> filter(OgcFilter ogcFilter) {
        List<DataObject> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(DataObject hazard : hazards) {
                if(hazard.getId().equals(featureId)) {
                    returnCollection.add(hazard);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<DataObject> getAll() {
        return Collections.unmodifiableList(hazards);
    }

    @Override
    public boolean removeAll(Collection<DataObject> hazards) {
        return HazardModel.hazards.removeAll(hazards);
    }

    @Override
    public boolean addAll(Collection<DataObject> newHazards) {
        return hazards.addAll(newHazards);
    }

    @Override
    public boolean add(DataObject newHazard) {
        return hazards.add(newHazard);
    }
}