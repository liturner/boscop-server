package de.turnertech.thw.cop.ows.model.unit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.util.BoundingBox;
import de.turnertech.thw.cop.util.BoundingBoxFilter;
import de.turnertech.thw.cop.util.Model;

public class UnitModel implements Model<Unit>, BoundingBoxFilter {
    
    public static final UnitModel INSTANCE = new UnitModel();

    public static final String TYPENAME = "boscop:Unit";

    private static final List<Unit> units = new LinkedList<>();

    private UnitModel() {

    }

    @Override
    public List<Unit> filter(BoundingBox boundingBox) {
        List<Unit> returnItems = new LinkedList<>();
        for(Unit unit : units) {
            if(boundingBox.contains(unit)) {
                returnItems.add(unit);
            }
        }
        return returnItems;
    }

    @Override
    public List<Unit> getAll() {
        return Collections.unmodifiableList(units);
    }

    @Override
    public boolean addAll(Collection<Unit> newUnits) {
        return units.addAll(newUnits);
    }

    @Override
    public boolean add(Unit newUnit) {
        return units.add(newUnit);
    }
}
