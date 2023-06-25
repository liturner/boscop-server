package de.turnertech.ows.filter;

import java.util.Objects;

import de.turnertech.ows.gml.IFeature;

public class IdOperator implements Operator {

    private final Id id;

    public IdOperator(final Id id) {
        this.id = id;
    }

    @Override
    public boolean test(IFeature feature) {
        return Objects.equals(id.toString(), feature.getId());
    }

    /**
     * @return the id
     */
    public Id getId() {
        return id;
    }

    
    
}
