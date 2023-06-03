package de.turnertech.ows.common;

import java.util.Collection;

import jakarta.servlet.ServletException;

public interface ModelEncoderProvider {
    
    public static final String GML32 = "application/gml+xml; version=3.2";

    public Collection<String> getSupportedFormats();

    public ModelEncoder getModelEncoder(OwsRequestContext requestContext, String format) throws ServletException;

}
