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

public class UnitModel implements Model {
    
    public static final UnitModel INSTANCE = new UnitModel();

    public static final String TYPENAME = "Unit";
    
    public static final String ID_FIELD = "id";
    
    public static final String OPTA_FIELD = "opta";

    public static final String GEOMETRY_FIELD = "geometry";

    private static final List<IFeature> features = new LinkedList<>();

    private final FeatureType featureType;

    private UnitModel() {
        featureType = new FeatureType(Constants.Model.NAMESPACE, TYPENAME);
        featureType.setSrs(SpatialReferenceSystem.EPSG4326);
        featureType.putProperty(new FeatureProperty(ID_FIELD, FeaturePropertyType.ID));
        featureType.putProperty(new FeatureProperty(OPTA_FIELD, FeaturePropertyType.TEXT));
        featureType.putProperty(new FeatureProperty(GEOMETRY_FIELD, FeaturePropertyType.POINT));
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

    @Override
    public File getStorageLocation() {
        return Paths.get(Settings.getDataDirectory().toString(), TYPENAME + ".gml").toFile();
    }
}
