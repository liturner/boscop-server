package de.turnertech.ows.filter;

public enum DistanceOperatorName {
    
    BEYOND("Beyond"),
    DWITHIN("DWithin");

    private final String xmlName;

    private DistanceOperatorName(final String xmlName) {
        this.xmlName = xmlName;
    }

    @Override
    public String toString() {
        return xmlName;
    }

    public static DistanceOperatorName fromString(final String comparator) {
        for(DistanceOperatorName entry : DistanceOperatorName.values()) {
            if(entry.toString().equals(comparator)) {
                return entry;
            }
        }
        return null;
    }

}
