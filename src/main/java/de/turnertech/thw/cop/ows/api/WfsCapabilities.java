package de.turnertech.thw.cop.ows.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.ows.parameter.OwsServiceValue;
import de.turnertech.thw.cop.ows.parameter.WfsVersionValue;

public class WfsCapabilities {
    
    private String serviceTitle;

    private String serviceAbstract;

    private List<String> keywords;

    private OwsServiceValue serviceType;

    private List<WfsVersionValue> serviceTypeVersions;

    private List<FeatureType> featureTypes;

    private Map<String, Boolean> filterConformance;

    private String fees;

    private String accessConstraints;

    public WfsCapabilities() {
        serviceTitle = "Default Service Title";
        serviceAbstract = "Default Service Abstract";
        serviceType = OwsServiceValue.WFS;
        serviceTypeVersions = Arrays.asList(WfsVersionValue.V2_0_2);
        keywords = Arrays.asList("Default Keyword");
        fees = "NONE";
        accessConstraints = "NONE";
        featureTypes = Collections.emptyList();
        
        filterConformance = new HashMap<>();
        filterConformance.put("ImplementsQuery", false);
        filterConformance.put("ImplementsAdHocQuery", false);
        filterConformance.put("ImplementsFunctions", false);
        filterConformance.put("ImplementsMinStandardFilter", false);
        filterConformance.put("ImplementsStandardFilter", false);
        filterConformance.put("ImplementsMinSpatialFilter", false);
        filterConformance.put("ImplementsSpatialFilter", false);
        filterConformance.put("ImplementsMinTemporalFilter", false);
        filterConformance.put("ImplementsTemporalFilter", false);
        filterConformance.put("ImplementsVersionNav", false);
        filterConformance.put("ImplementsSorting", false);
        filterConformance.put("ImplementsExtendedOperators", false);
    }

    /**
     * @return the serviceTitle
     */
    public String getServiceTitle() {
        return serviceTitle;
    }

    /**
     * @param serviceTitle the serviceTitle to set
     */
    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    /**
     * @return the serviceAbstract
     */
    public String getServiceAbstract() {
        return serviceAbstract;
    }

    /**
     * @param serviceAbstract the serviceAbstract to set
     */
    public void setServiceAbstract(String serviceAbstract) {
        this.serviceAbstract = serviceAbstract;
    }

    /**
     * @return the serviceType
     */
    public OwsServiceValue getServiceType() {
        return serviceType;
    }

    /**
     * @return the serviceTypeVersions
     */
    public List<WfsVersionValue> getServiceTypeVersions() {
        return serviceTypeVersions;
    }

    /**
     * @param serviceTypeVersions the serviceTypeVersions to set
     */
    public void setServiceTypeVersions(List<WfsVersionValue> serviceTypeVersions) {
        this.serviceTypeVersions = serviceTypeVersions;
    }

    /**
     * @return the fees
     */
    public String getFees() {
        return fees;
    }

    /**
     * @param fees the fees to set
     */
    public void setFees(String fees) {
        this.fees = fees;
    }

    /**
     * @return the accessConstraints
     */
    public String getAccessConstraints() {
        return accessConstraints;
    }

    /**
     * @param accessConstraints the accessConstraints to set
     */
    public void setAccessConstraints(String accessConstraints) {
        this.accessConstraints = accessConstraints;
    }

    /**
     * @return the keywords
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * @return the featureTypes
     */
    public List<FeatureType> getFeatureTypes() {
        return featureTypes;
    }

    /**
     * @param featureTypes the featureTypes to set
     */
    public void setFeatureTypes(List<FeatureType> featureTypes) {
        this.featureTypes = featureTypes;
    }

    /**
     * @return the filterConformance
     */
    public Map<String, Boolean> getFilterConformance() {
        return filterConformance;
    }

    

}
