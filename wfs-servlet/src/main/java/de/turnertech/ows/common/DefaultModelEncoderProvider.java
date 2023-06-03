package de.turnertech.ows.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import jakarta.servlet.ServletException;

public class DefaultModelEncoderProvider implements ModelEncoderProvider {

    private static final Collection<String> supportedFormats = Collections.unmodifiableCollection(Arrays.asList(GML32));

    @Override
    public ModelEncoder getModelEncoder(OwsRequestContext requestContext, String format) throws ServletException {
        if(GML32.equals(format)) {
            return new Gml32ModelEncoder();
        }
        throw new ServletException(DefaultModelEncoderProvider.class.getSimpleName() + " cannot decode: " + format == null ? "null" : format);
    }

    @Override
    public Collection<String> getSupportedFormats() {
        return supportedFormats;
    }
    
}
