package de.turnertech.thw.cop.model.hazard;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.ows.api.Model;
import de.turnertech.thw.cop.ows.filter.OgcFilter;
import de.turnertech.thw.cop.util.BoundingBoxFilter;

public class HazardModel implements Model, BoundingBoxFilter {
    
    public static final HazardModel INSTANCE = new HazardModel();

    public static final String NAME = "Hazard";

    public static final String TYPENAME = "boscop:" + NAME;

    private static final List<Feature> hazards = new LinkedList<>();

    private HazardModel() {

    }

    @Override
    public List<Feature> filter(BoundingBox boundingBox) {
        List<Feature> returnItems = new LinkedList<>();
        for(Feature hazard : hazards) {
            if(boundingBox.contains(hazard)) {
                returnItems.add(hazard);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<Feature> filter(OgcFilter ogcFilter) {
        List<Feature> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(Feature hazard : hazards) {
                if(hazard.getId().equals(featureId)) {
                    returnCollection.add(hazard);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<Feature> getAll() {
        return Collections.unmodifiableList(hazards);
    }

    @Override
    public boolean removeAll(Collection<Feature> hazards) {
        return HazardModel.hazards.removeAll(hazards);
    }

    @Override
    public boolean addAll(Collection<Feature> newHazards) {
        return hazards.addAll(newHazards);
    }

    @Override
    public boolean add(Feature newHazard) {
        return hazards.add(newHazard);
    }
}