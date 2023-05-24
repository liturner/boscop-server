package de.turnertech.thw.cop.model.area;

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

public class AreaModel implements Model {
    
    public static final AreaModel INSTANCE = new AreaModel();

    public static final String NAME = "Area";

    public static final String TYPENAME = NAME;

    private static final List<IFeature> features = new LinkedList<>();

    private final FeatureType featureType;

    private AreaModel() {
        featureType = new FeatureType(Constants.Model.NAMESPACE, TYPENAME);
        featureType.setSrs(SpatialReferenceSystem.EPSG4327);
        featureType.setTitle(NAME);
    }

    @Override
    public List<IFeature> filter(BoundingBox boundingBox) {
        List<IFeature> returnItems = new LinkedList<>();
        for(IFeature area : features) {
            if(boundingBox.intersects(area.getBoundingBox())) {
                returnItems.add(area);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<IFeature> filter(OgcFilter ogcFilter) {
        List<IFeature> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(IFeature area : features) {
                if(area.getId().equals(featureId)) {
                    returnCollection.add(area);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<IFeature> getAll() {
        return Collections.unmodifiableList(features);
    }

    public boolean removeAll(Collection<IFeature> areas) {
        return AreaModel.features.removeAll(areas);
    }

    @Override
    public boolean addAll(Collection<IFeature> dataObjects) {
        return features.addAll(dataObjects);
    }

    @Override
    public boolean add(IFeature newArea) {
        return features.add(newArea);
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
