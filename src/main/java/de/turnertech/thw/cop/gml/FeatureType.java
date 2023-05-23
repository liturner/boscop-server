package de.turnertech.thw.cop.gml;

public class FeatureType {
    
    /**
     * The "type name". This is the parameter which will be used in requests usw.
     */
    private final String name;

    private final String namespace;

    private SpatialReferenceSystem srs;

    private String title;

    public FeatureType(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the srs
     */
    public SpatialReferenceSystem getSrs() {
        return srs;
    }

    /**
     * @param srs the srs to set
     */
    public void setSrs(SpatialReferenceSystem srs) {
        this.srs = srs;
    }

    

}
