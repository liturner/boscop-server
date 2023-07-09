package de.turnertech.ows.servlet;

import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class Insert implements TransactionAction, StandardInputParameters {
    
    public static final QName QNAME = new QName(OwsContext.WFS_URI, "Insert");

    private final List<IFeature> value;

    private String handle;

    private SpatialReferenceSystem srsName;

    private String inputFormat;

    public Insert() {
        this.value = new LinkedList<>();
        this.inputFormat = "application/gml+xml; version=3.2";
    }

    /**
     * @return the value
     */
    public List<IFeature> getValue() {
        return value;
    }

    @Override
    public String getHandle() {
        return handle;
    }

    /**
     * @param handle the handle to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    @Override
    public SpatialReferenceSystem getSrsName() {
        return srsName;
    }

    @Override
    public String getInputFormat() {
        return inputFormat;
    }

    /**
     * @param srsName the srsName to set
     */
    public void setSrsName(SpatialReferenceSystem srsName) {
        this.srsName = srsName;
    }

    /**
     * @param inputFormat the inputFormat to set
     */
    public void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }

    
}
