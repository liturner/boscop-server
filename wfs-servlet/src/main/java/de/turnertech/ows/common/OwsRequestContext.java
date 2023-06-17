package de.turnertech.ows.common;

import java.util.ArrayList;
import java.util.List;

import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.parameter.OwsServiceValue;
import de.turnertech.ows.parameter.WfsVersionValue;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;

public class OwsRequestContext {

    private OwsServiceValue owsService;

    public WfsVersionValue owsVersion;

    public List<FeatureType> featureTypes;

    public SpatialReferenceSystemRepresentation requestedSrs;

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

    public SpatialReferenceSystemRepresentation getRequestedSrs() {
        return requestedSrs;
    }

    public void setRequestedSrs(SpatialReferenceSystemRepresentation requestedSrs) {
        this.requestedSrs = requestedSrs;
    }

}
