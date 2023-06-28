package de.turnertech.ows.filter;

import javax.xml.namespace.QName;

import de.turnertech.ows.common.OwsContext;

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

    private final QName xmlName;

    private SpatialOperatorName(final String xmlName) {
        this.xmlName = new QName(OwsContext.FES_URI, xmlName);
    }

    @Override
    public String toString() {
        return xmlName.getLocalPart();
    }

    public QName getQName() {
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

    public static SpatialOperatorName valueOf(final QName qname) {
        for(SpatialOperatorName entry : SpatialOperatorName.values()) {
            if(entry.getQName().equals(qname)) {
                return entry;
            }
        }
        return null;
    }

}
