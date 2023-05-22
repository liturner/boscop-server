package de.turnertech.thw.cop.ows.api;

import java.util.Arrays;
import java.util.Collection;

import de.turnertech.thw.cop.ows.parameter.WfsVersionValue;
import jakarta.servlet.ServletException;

public abstract class OwsContextFactory {
    
    public OwsContext createOwsContext() throws ServletException {
        DefaultOwsContext owsContext = new DefaultOwsContext();
        owsContext.setModelProvider(createModelProvider());
        if(owsContext.getModelProvider() == null) {
            throw new ServletException(OwsContextFactory.class.getSimpleName() + " returned null " + ModelProvider.class.getSimpleName());
        }

        owsContext.setSupportedWfsVersions(getSupportedWfsVersions());
        if(owsContext.getSupportedWfsVersions() == null) {
            throw new ServletException(OwsContextFactory.class.getSimpleName() + " returned null supported Wfs versions (use an empty list instead)");
        }

        return owsContext;
    }

    public abstract ModelProvider createModelProvider();

    public Collection<WfsVersionValue> getSupportedWfsVersions() {
        return Arrays.asList(WfsVersionValue.V2_0_2);
    }

}
