package de.turnertech.ows.common;

public class DefaultOwsContextFactory extends OwsContextFactory {

    @Override
    public ModelProvider createModelProvider() {
        return new DefaultModelProvider();
    }

    @Override
    public WfsCapabilities getWfsCapabilities() {
        return new WfsCapabilities();
    }
    
}
