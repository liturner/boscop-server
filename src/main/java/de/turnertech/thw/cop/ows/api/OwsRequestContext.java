package de.turnertech.thw.cop.ows.api;

import java.util.ArrayList;
import java.util.List;

import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.ows.parameter.OwsServiceValue;
import de.turnertech.thw.cop.ows.parameter.WfsVersionValue;

public class OwsRequestContext {

    private OwsServiceValue owsService;

    public WfsVersionValue owsVersion;

    public List<FeatureType> featureTypes;

    public OwsRequestContext() {
        owsVersion = null;
        featureTypes = new ArrayList<>(1);
    }

    /**
     * @return the owsVersion
     */
    public WfsVersionValue getOwsVersion() {
        return owsVersion;
    }

    /**
     * @param owsVersion the owsVersion to set
     */
    public void setOwsVersion(WfsVersionValue owsVersion) {
        this.owsVersion = owsVersion;
    }

    /**
     * @return the owsService
     */
    public OwsServiceValue getOwsService() {
        return owsService;
    }

    /**
     * @param owsService the owsService to set
     */
    public void setOwsService(OwsServiceValue owsService) {
        this.owsService = owsService;
    }

    /**
     * @return the featureTypes
     */
    public List<FeatureType> getFeatureTypes() {
        return featureTypes;
    }

}
