package de.turnertech.ows.gml;

import java.util.Deque;
import java.util.LinkedList;

import de.turnertech.ows.srs.SpatialReferenceSystem;

public class GmlDecoderContext {
    
    private FeatureType featureType;

    private FeatureIdRetriever featureIdRetriever;

    private Deque<SpatialReferenceSystem> srsDeque;

    public GmlDecoderContext() {
        featureType = null;
        featureIdRetriever = new UuidFeatureIdRetriever();
        srsDeque = new LinkedList<>();
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

    public Deque<SpatialReferenceSystem> getSrsDeque() {
        return srsDeque;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    

}
