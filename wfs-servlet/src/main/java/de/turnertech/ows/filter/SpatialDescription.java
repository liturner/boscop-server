package de.turnertech.ows.filter;

import de.turnertech.ows.gml.Envelope;

public class SpatialDescription {
    
    private final Envelope envelope;

    private final ValueReference valueReference;

    public SpatialDescription(final Envelope envelope) {
        this.envelope = envelope;
        this.valueReference = null;
    }

    public SpatialDescription(final ValueReference valueReference) {
        this.envelope = null;
        this.valueReference = valueReference;
    }

    /**
     * @return the envelope
     */
    public Envelope getEnvelope() {
        return envelope;
    }

    /**
     * @return the valueReference
     */
    public ValueReference getValueReference() {
        return valueReference;
    }

}
