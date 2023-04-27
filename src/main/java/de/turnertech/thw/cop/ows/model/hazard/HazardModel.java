package de.turnertech.thw.cop.ows.model.hazard;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.turnertech.thw.cop.util.BoundingBox;
import de.turnertech.thw.cop.util.BoundingBoxFilter;
import de.turnertech.thw.cop.util.Model;

public class HazardModel implements Model<Hazard>, BoundingBoxFilter {
    
    public static final HazardModel INSTANCE = new HazardModel();

    public static final String NAME = "Hazard";

    public static final String TYPENAME = "boscop:" + NAME;

    private static final List<Hazard> hazards = new LinkedList<>();

    private HazardModel() {

    }

    @Override
    public List<Hazard> filter(BoundingBox boundingBox) {
        List<Hazard> returnItems = new LinkedList<>();
        for(Hazard hazard : hazards) {
            if(boundingBox.contains(hazard)) {
                returnItems.add(hazard);
            }
        }
        return returnItems;
    }

    @Override
    public List<Hazard> getAll() {
        return Collections.unmodifiableList(hazards);
    }

    @Override
    public boolean addAll(Collection<Hazard> newHazards) {
        return hazards.addAll(newHazards);
    }

    @Override
    public boolean add(Hazard newHazard) {
        return hazards.add(newHazard);
    }
}