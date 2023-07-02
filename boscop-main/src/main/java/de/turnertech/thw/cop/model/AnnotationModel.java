package de.turnertech.thw.cop.model;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.ows.common.Model;
import de.turnertech.ows.filter.OgcFilter;
import de.turnertech.ows.gml.Envelope;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.FeaturePropertyType;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.Settings;

public class AnnotationModel implements Model {
    
    public static final AnnotationModel INSTANCE = new AnnotationModel();

    public static final String NAME = "Annotation";

    public static final String TYPENAME = NAME;

    private static final List<IFeature> features = new LinkedList<>();

    private final FeatureType featureType;

    private AnnotationModel() {
        featureType = new FeatureType(Constants.Model.NAMESPACE, TYPENAME);
        featureType.setSrs(SpatialReferenceSystem.CRS84);
        featureType.setTitle(NAME);
        featureType.putProperty(new FeatureProperty("id", FeaturePropertyType.ID));
        featureType.putProperty(new FeatureProperty("geometry", FeaturePropertyType.GEOMETRY));
    }

    @Override
    public List<IFeature> filter(Envelope boundingBox) {
        List<IFeature> returnItems = new LinkedList<>();
        for(IFeature hazard : features) {
            if(boundingBox.intersects(hazard.getBoundingBox())) {
                returnItems.add(hazard);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<IFeature> filter(OgcFilter ogcFilter) {
        List<IFeature> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(IFeature hazard : features) {
                if(hazard.getId().equals(featureId)) {
                    returnCollection.add(hazard);
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
    public boolean removeAll(Collection<IFeature> hazards) {
        return features.removeAll(hazards);
    }

    @Override
    public boolean addAll(Collection<IFeature> newHazards) {
        return features.addAll(newHazards);
    }

    @Override
    public boolean add(IFeature newHazard) {
        return features.add(newHazard);
    }

    @Override
    public FeatureType getFeatureType() {
        return featureType;
    }

    @Override
    public Envelope getBoundingBox() {
        Envelope boundingBox = null;
        if(features.size() > 0) {
            IFeature firstFeature = features.get(0);
            boundingBox = Envelope.from(firstFeature.getBoundingBox());
        } 
        for (IFeature feature : features) {
            boundingBox.expandToFit(feature.getBoundingBox());
        }
        return boundingBox;
    }

    @Override
    public File getStorageLocation() {
        return Paths.get(Settings.getDataDirectory().toString(), TYPENAME + ".gml").toFile();
    }
}