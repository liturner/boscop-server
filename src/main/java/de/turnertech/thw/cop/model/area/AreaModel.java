package de.turnertech.thw.cop.model.area;

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

public class AreaModel implements Model {
    
    public static final AreaModel INSTANCE = new AreaModel();

    public static final String NAME = "Area";

    public static final String TYPENAME = NAME;

    private static final List<Feature> features = new LinkedList<>();

    private final FeatureType featureType;

    private AreaModel() {
        featureType = new FeatureType(Constants.Model.NAMESPACE, TYPENAME);
        featureType.setSrs(SpatialReferenceSystem.EPSG4327);
        featureType.setTitle(NAME);
    }

    @Override
    public List<Feature> filter(BoundingBox boundingBox) {
        List<Feature> returnItems = new LinkedList<>();
        for(Feature area : features) {
            if(boundingBox.intersects(area.getBoundingBox())) {
                returnItems.add(area);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<Feature> filter(OgcFilter ogcFilter) {
        List<Feature> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(Feature area : features) {
                if(area.getId().equals(featureId)) {
                    returnCollection.add(area);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<Feature> getAll() {
        return Collections.unmodifiableList(features);
    }

    public boolean removeAll(Collection<Feature> areas) {
        return AreaModel.features.removeAll(areas);
    }

    @Override
    public boolean addAll(Collection<Feature> dataObjects) {
        return features.addAll(dataObjects);
    }

    @Override
    public boolean add(Feature newArea) {
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
        for (Feature feature : features) {
            boundingBox.expandToFit(feature.getBoundingBox());
        }
        return boundingBox;
    }

}
