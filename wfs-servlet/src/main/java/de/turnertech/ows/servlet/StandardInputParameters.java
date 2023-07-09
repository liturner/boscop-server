package de.turnertech.ows.servlet;

import de.turnertech.ows.srs.SpatialReferenceSystem;

public interface StandardInputParameters {
    
    public SpatialReferenceSystem getSrsName();

    public String getInputFormat();

}
