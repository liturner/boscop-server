package de.turnertech.thw.cop.ows.model.area;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.util.BoundingBox;
import de.turnertech.thw.cop.util.BoundingBoxFilter;
import de.turnertech.thw.cop.util.Model;

public class AreaModel implements Model<Area>, BoundingBoxFilter {
    
    public static final AreaModel INSTANCE = new AreaModel();

    public static final String NAME = "Area";

    public static final String TYPENAME = "boscop:" + NAME;

    private static final List<Area> areas = new LinkedList<>();

    private AreaModel() {

    }

    @Override
    public List<Area> filter(BoundingBox boundingBox) {
        List<Area> returnItems = new LinkedList<>();
        for(Area area : areas) {
            if(boundingBox.contains(area.getGeometry())) {
                returnItems.add(area);
            }
        }
        return returnItems;
    }

    @Override
    public List<Area> getAll() {
        return Collections.unmodifiableList(areas);
    }

    @Override
    public boolean addAll(Collection<Area> dataObjects) {
        return areas.addAll(dataObjects);
    }

    @Override
    public boolean add(Area newArea) {
        return areas.add(newArea);
    }
}
