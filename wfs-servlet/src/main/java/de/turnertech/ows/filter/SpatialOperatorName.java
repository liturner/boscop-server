package de.turnertech.ows.filter;

public enum SpatialOperatorName {
    
    BBOX("BBOX"),
    EQUALS("Equals"),
    DISJOINT("Disjoint"),
    INTERSECTS("Intersects"),
    TOUCHES("Touches"),
    CROSSES("Crosses"),
    WITHIN("Within"),
    CONTAINS("Contains"),
    OVERLAPS("Overlaps");

    private final String xmlName;

    private SpatialOperatorName(final String xmlName) {
        this.xmlName = xmlName;
    }

    @Override
    public String toString() {
        return xmlName;
    }

    public static SpatialOperatorName fromString(final String comparator) {
        for(SpatialOperatorName entry : SpatialOperatorName.values()) {
            if(entry.toString().equals(comparator)) {
                return entry;
            }
        }
        return null;
    }

}
