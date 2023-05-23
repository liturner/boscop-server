package de.turnertech.thw.cop.model.unit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.ows.api.Model;
import de.turnertech.thw.cop.ows.filter.OgcFilter;

public class UnitModel implements Model {
    
    public static final UnitModel INSTANCE = new UnitModel();

    public static final String TYPENAME = "Unit";

    private static final List<Feature> features = new LinkedList<>();

    private UnitModel() {
        
    }

    @Override
    public List<Feature> filter(BoundingBox boundingBox) {
        List<Feature> returnItems = new LinkedList<>();
        for(Feature unit : features) {
            if(boundingBox.intersects(unit.getBoundingBox())) {
                returnItems.add(unit);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<Feature> filter(OgcFilter ogcFilter) {
        List<Feature> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(Feature unit : features) {
                if(unit.getId().equals(featureId)) {
                    returnCollection.add(unit);
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
    public boolean addAll(Collection<Feature> newUnits) {
        return features.addAll(newUnits);
    }

    public boolean removeAll(Collection<Feature> units) {
        return UnitModel.features.removeAll(units);
    }

    @Override
    public boolean add(Feature newUnit) {
        return features.add(newUnit);
    }

    @Override
    public FeatureType getFeatureType() {
        return Unit.FEATURE_TYPE;
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
