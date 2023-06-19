package de.turnertech.thw.cop.model;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.ows.common.Model;
import de.turnertech.ows.filter.OgcFilter;
import de.turnertech.ows.gml.BoundingBox;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.FeaturePropertyType;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.Settings;

public class LineModel implements Model {
    
    public static final LineModel INSTANCE = new LineModel();

    public static final String NAME = "Line";

    public static final String TYPENAME = NAME;

    private static final List<IFeature> features = new LinkedList<>();

    private final FeatureType featureType;

    private LineModel() {
        featureType = new FeatureType(Constants.Model.NAMESPACE, TYPENAME);
        featureType.setSrs(SpatialReferenceSystem.CRS84);
        featureType.setTitle(NAME);

        featureType.putProperty(new FeatureProperty("id", FeaturePropertyType.ID));
        featureType.putProperty(new FeatureProperty("lineType", FeaturePropertyType.TEXT));
        featureType.putProperty(new FeatureProperty("geometry", FeaturePropertyType.LINE_STRING));
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
        return features.removeAll(areas);
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

    @Override
    public File getStorageLocation() {
        return Paths.get(Settings.getDataDirectory().toString(), NAME + ".gml").toFile();
    }
}
