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
import de.turnertech.ows.gml.DirectPosition;
import de.turnertech.ows.gml.DirectPositionList;
import de.turnertech.ows.gml.Feature;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.FeaturePropertyType;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.gml.LineString;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.Settings;
import mil.nga.grid.features.Bounds;
import mil.nga.mgrs.features.GridLine;
import mil.nga.mgrs.grid.Grid;
import mil.nga.mgrs.grid.GridType;
import mil.nga.mgrs.grid.Grids;
import mil.nga.mgrs.gzd.GridZone;
import mil.nga.mgrs.gzd.GridZones;

public class GridModel implements Model {
    
    public static final GridModel INSTANCE = new GridModel();

    public static final String NAME = "Grid";

    public static final String TYPENAME = NAME;

    private static final List<IFeature> features = new LinkedList<>();

    private final FeatureType featureType;

    private GridModel() {
        featureType = new FeatureType(Constants.Model.NAMESPACE, TYPENAME);
        featureType.setSrs(SpatialReferenceSystem.CRS84);
        featureType.setTitle(NAME);

        featureType.putProperty(new FeatureProperty("id", FeaturePropertyType.ID));
        featureType.putProperty(new FeatureProperty("label", FeaturePropertyType.TEXT));
        featureType.putProperty(new FeatureProperty("line", FeaturePropertyType.LINE_STRING));
    }

    public void populate() {
        Grid kmGrid = Grids.create().getGrid(GridType.TEN_KILOMETER);
        /**
         * GZD - 0 - unl
         * 100km - 5 - 8
         * 10km - 9 - 11
         * km - 12 - 14
         * 100m - 15 - 17
         */


        GridZone gridZone = GridZones.getGridZone(32, 'U');
        Bounds bigBounds = Bounds.degrees(0, 0, 90, 90);
        List<GridLine> lines = kmGrid.getLines(bigBounds, gridZone);

        for(GridLine line : lines) {
            Feature newLine = featureType.createInstance();
            newLine.setPropertyValue("label", "temp");
            newLine.setPropertyValue("line", new LineString(
                new DirectPositionList(
                    new DirectPosition(line.getPoint1().getX(), line.getPoint1().getY()), 
                    new DirectPosition(line.getPoint2().getX(), line.getPoint2().getY()))));
            features.add(newLine);
        }
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
