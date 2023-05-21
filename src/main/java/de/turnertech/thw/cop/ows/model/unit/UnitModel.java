package de.turnertech.thw.cop.ows.model.unit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.ows.filter.OgcFilter;
import de.turnertech.thw.cop.util.BoundingBox;
import de.turnertech.thw.cop.util.BoundingBoxFilter;
import de.turnertech.thw.cop.util.DataObject;
import de.turnertech.thw.cop.util.Model;

public class UnitModel implements Model, BoundingBoxFilter {
    
    public static final UnitModel INSTANCE = new UnitModel();

    public static final String TYPENAME = "boscop:Unit";

    private static final List<DataObject> units = new LinkedList<>();

    private UnitModel() {

    }

    @Override
    public List<DataObject> filter(BoundingBox boundingBox) {
        List<DataObject> returnItems = new LinkedList<>();
        for(DataObject unit : units) {
            if(boundingBox.contains(unit)) {
                returnItems.add(unit);
            }
        }
        return returnItems;
    }

    @Override
    public Collection<DataObject> filter(OgcFilter ogcFilter) {
        List<DataObject> returnCollection = new LinkedList<>();
        for(String featureId : ogcFilter.getFeatureIdFilters()) {
            for(DataObject unit : units) {
                if(unit.getId().equals(featureId)) {
                    returnCollection.add(unit);
                }
            }
        }
        return returnCollection;
    }

    @Override
    public List<DataObject> getAll() {
        return Collections.unmodifiableList(units);
    }

    @Override
    public boolean addAll(Collection<DataObject> newUnits) {
        return units.addAll(newUnits);
    }

    public boolean removeAll(Collection<DataObject> units) {
        return UnitModel.units.removeAll(units);
    }

    @Override
    public boolean add(DataObject newUnit) {
        return units.add(newUnit);
    }
}
