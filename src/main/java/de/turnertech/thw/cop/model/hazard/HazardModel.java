package de.turnertech.thw.cop.model.hazard;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.gml.SpatialReferenceSystem;
import de.turnertech.thw.cop.ows.api.Model;
import de.turnertech.thw.cop.ows.filter.OgcFilter;

public class HazardModel implements Model {
    
    public static final HazardModel INSTANCE = new HazardModel();

    public static final String NAME = "Hazard";

    public static final String TYPENAME = NAME;

    private static final List<Feature> features = new LinkedList<>();

    private final FeatureType featureType;

    private HazardModel() {
        featureType = new FeatureType(Constants.Model.NAMESPACE, TYPENAME);
        featureType.setSrs(SpatialReferenceSystem.EPSG4327);
    }

    @Override
    public List<Feature> filter(BoundingBox boundingBox) {
        List<Feature> returnItems = new LinkedList<>();
        for(Feature hazard : features) {
            if(boundingBox.intersects(hazard.getBoundingBox())) {
                returnItems.add(hazard);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<Feature> filter(OgcFilter ogcFilter) {
        List<Feature> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(Feature hazard : features) {
                if(hazard.getId().equals(featureId)) {
                    returnCollection.add(hazard);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<Feature> getAll() {
        return Collections.unmodifiableList(features);
    }

    @Override
    public boolean removeAll(Collection<Feature> hazards) {
        return HazardModel.features.removeAll(hazards);
    }

    @Override
    public boolean addAll(Collection<Feature> newHazards) {
        return features.addAll(newHazards);
    }

    @Override
    public boolean add(Feature newHazard) {
        return features.add(newHazard);
    }

    @Override
    public FeatureType getFeatureType() {
        return featureType;
    }

    @Override
    public BoundingBox getBoundingBox() {
        BoundingBox boundingBox = null;
        if(features.size() > 0) {
            boundingBox = BoundingBox.from(features.get(0).getBoundingBox());
        } 
        for (Feature feature : features) {
            boundingBox.expandToFit(feature.getBoundingBox());
        }
        return boundingBox;
    }
}