package de.turnertech.ows.filter;

public enum Conformance {
    
    IMPLEMENTS_QUERY("", false),
    IMPLEMENTS_AD_HOC_QUERY("", false),
    IMPLEMENTS_FUNCTIONS("", false),
    IMPLEMENTS_MIN_STANDARD_FILTER("", false),
    IMPLEMENTS_MIN_SPATIAL_FILTER("", false),
    IMPLEMENTS_SPATIAL_FILTER("", false),
    IMPLEMENTS_MIN_TEMPORAL_FILTER("", false),
    IMPLEMENTS_TEMPORAL_FILTER("", false),
    IMPLEMENTS_VERSION_NAV("", false),
    IMPLEMENTS_SORTING("", false),
    IMPLEMENTS_EXTENDED_OPERATORS("", false);

    private final String representation;

    private final boolean conformant;

    private Conformance(final String representation, final boolean conforms) {
        this.representation = representation;
        this.conformant = conforms;
    }

    public boolean isConformant() {
        return conformant;
    }

    @Override
    public String toString() {
        return representation;
    }

}
