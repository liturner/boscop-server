package de.turnertech.ows.servlet;

import de.turnertech.ows.parameter.OwsServiceValue;
import de.turnertech.ows.parameter.WfsVersionValue;

/**
 * See Spec Docs
 */
public abstract class BaseRequest {
    
    private OwsServiceValue service;

    private WfsVersionValue version;

    private String handle;

    /**
     * @return the service
     */
    public OwsServiceValue getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(OwsServiceValue service) {
        this.service = service;
    }

    /**
     * @return the version
     */
    public WfsVersionValue getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(WfsVersionValue version) {
        this.version = version;
    }

    /**
     * @return the handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * @param handle the handle to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

}
