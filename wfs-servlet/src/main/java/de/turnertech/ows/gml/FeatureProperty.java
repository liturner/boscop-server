package de.turnertech.ows.gml;

public class FeatureProperty {
    
    private String name;

    private int minOccurs = 1;
    
    private int maxOccurs = 1;

    private FeaturePropertyType propertyType;

    public FeatureProperty() {
        this(null, null);
    }

    public FeatureProperty(String name, FeaturePropertyType propertyType) {
        this.name = name;
        this.propertyType = propertyType;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the minOccurs
     */
    public int getMinOccurs() {
        return minOccurs;
    }

    /**
     * @param minOccurs the minOccurs to set
     */
    public void setMinOccurs(int minOccurs) {
        this.minOccurs = minOccurs;
    }

    /**
     * @return the maxOccurs
     */
    public int getMaxOccurs() {
        return maxOccurs;
    }

    /**
     * @param maxOccurs the maxOccurs to set
     */
    public void setMaxOccurs(int maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    /**
     * @return the propertyType
     */
    public FeaturePropertyType getPropertyType() {
        return propertyType;
    }

    /**
     * @param propertyType the propertyType to set
     */
    public void setPropertyType(FeaturePropertyType propertyType) {
        this.propertyType = propertyType;
    }
    
}
