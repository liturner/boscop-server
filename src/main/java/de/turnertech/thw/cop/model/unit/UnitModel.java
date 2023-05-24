package de.turnertech.thw.cop.model.unit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.gml.IFeature;
import de.turnertech.thw.cop.gml.SpatialReferenceSystem;
import de.turnertech.thw.cop.ows.api.Model;
import de.turnertech.thw.cop.ows.filter.OgcFilter;

public class UnitModel implements Model {
    
    public static final UnitModel INSTANCE = new UnitModel();

    public static final String TYPENAME = "Unit";

    private static final List<IFeature> features = new LinkedList<>();

    private final FeatureType featureType;

    private UnitModel() {
        featureType = new FeatureType(Constants.Model.NAMESPACE, TYPENAME);
        featureType.setSrs(SpatialReferenceSystem.EPSG4327);
    }

    @Override
    public List<IFeature> filter(BoundingBox boundingBox) {
        List<IFeature> returnItems = new LinkedList<>();
        for(IFeature unit : features) {
            if(boundingBox.intersects(unit.getBoundingBox())) {
                returnItems.add(unit);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<IFeature> filter(OgcFilter ogcFilter) {
        List<IFeature> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(IFeature unit : features) {
                if(unit.getId().equals(featureId)) {
                    returnCollection.add(unit);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<IFeature> getAll() {
        return Collections.unmodifiableList(features);
    }

    @Override
    public boolean addAll(Collection<IFeature> newUnits) {
        return features.addAll(newUnits);
    }

    public boolean removeAll(Collection<IFeature> units) {
        return UnitModel.features.removeAll(units);
    }

    @Override
    public boolean add(IFeature newUnit) {
        return features.add(newUnit);
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
        for (IFeature feature : features) {
            boundingBox.expandToFit(feature.getBoundingBox());
        }
        return boundingBox;
    }
}
