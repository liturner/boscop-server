package de.turnertech.thw.cop.gml;

public class GmlDecoderContext {
    
    private FeatureIdRetriever featureIdRetriever;

    public GmlDecoderContext() {
        featureIdRetriever = new UuidFeatureIdRetriever();
    }

    /**
     * @return the featureIdRetriever
     */
    public FeatureIdRetriever getFeatureIdRetriever() {
        return featureIdRetriever;
    }

    /**
     * @param featureIdRetriever the featureIdRetriever to set
     */
    public void setFeatureIdRetriever(FeatureIdRetriever featureIdRetriever) {
        this.featureIdRetriever = featureIdRetriever;
    }

}
