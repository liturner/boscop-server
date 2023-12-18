package de.turnertech.thw.cop.model;

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
import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class HazardModel implements Model {

    public static final HazardModel INSTANCE = new HazardModel();

    public static final String NAME = "Hazard";

    public static final String TYPENAME = NAME;

    private static final List<IFeature> features = new LinkedList<>();

    private final FeatureType featureType;

    private HazardModel() {
        featureType = new FeatureType(Constants.Model.NAMESPACE, TYPENAME);
        featureType.setSrs(SpatialReferenceSystem.CRS84);
        featureType.setTitle(NAME);
        featureType.setDescription("A feature located at a specific single point location.");
        featureType.putProperty(new FeatureProperty("id", FeaturePropertyType.ID));
        featureType.putProperty(new FeatureProperty("hazardType", FeaturePropertyType.TEXT));
        featureType.putProperty(new FeatureProperty("geometry", FeaturePropertyType.POINT));
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
        return HazardModel.features.removeAll(hazards);
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

    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void persist() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}