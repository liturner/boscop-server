package de.turnertech.thw.cop.model.unit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.ows.api.Model;
import de.turnertech.thw.cop.ows.filter.OgcFilter;
import de.turnertech.thw.cop.util.BoundingBoxFilter;

public class UnitModel implements Model, BoundingBoxFilter {
    
    public static final UnitModel INSTANCE = new UnitModel();

    public static final String TYPENAME = "boscop:Unit";

    private static final List<Feature> units = new LinkedList<>();

    private UnitModel() {

    }

    @Override
    public List<Feature> filter(BoundingBox boundingBox) {
        List<Feature> returnItems = new LinkedList<>();
        for(Feature unit : units) {
            if(boundingBox.contains(unit)) {
                returnItems.add(unit);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<Feature> filter(OgcFilter ogcFilter) {
        List<Feature> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(Feature unit : units) {
                if(unit.getId().equals(featureId)) {
                    returnCollection.add(unit);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<Feature> getAll() {
        return Collections.unmodifiableList(units);
    }

    @Override
    public boolean addAll(Collection<Feature> newUnits) {
        return units.addAll(newUnits);
    }

    public boolean removeAll(Collection<Feature> units) {
        return UnitModel.units.removeAll(units);
    }

    @Override
    public boolean add(Feature newUnit) {
        return units.add(newUnit);
    }
}
