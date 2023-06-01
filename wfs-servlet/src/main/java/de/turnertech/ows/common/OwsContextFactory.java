package de.turnertech.ows.common;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;

public abstract class OwsContextFactory {
    
    public final OwsContext createOwsContext() throws ServletException {
        DefaultOwsContext owsContext = new DefaultOwsContext();
        owsContext.setModelProvider(createModelProvider());
        if(owsContext.getModelProvider() == null) {
            throw new ServletException(OwsContextFactory.class.getSimpleName() + " returned null " + ModelProvider.class.getSimpleName());
        }

        owsContext.setWfsCapabilities(getWfsCapabilities());
        if(owsContext.getWfsCapabilities() == null) {
            throw new ServletException(OwsContextFactory.class.getSimpleName() + " returned null WfsCapabilities");
        }

        owsContext.setXmlNamespacePrefixMap(getNamespacePrefixMap());
        if(owsContext.getXmlNamespacePrefixMap() == null) {
            throw new ServletException(OwsContextFactory.class.getSimpleName() + " returned null Namespace Prefix Map");
        }

        owsContext.setXmlNamespaceSchemaMap(getNamespaceSchemaMap());
        if(owsContext.getXmlNamespaceSchemaMap() == null) {
            throw new ServletException(OwsContextFactory.class.getSimpleName() + " returned null Namespace Schema Map");
        }

        owsContext.setModelEncoderProvider(getModelEncoderProvider());
        if(owsContext.getXmlNamespaceSchemaMap() == null) {
            throw new ServletException(OwsContextFactory.class.getSimpleName() + " returned null Model Encoder Provider");
        }

        return owsContext;
    }

    public abstract ModelProvider createModelProvider();

    public ModelEncoderProvider getModelEncoderProvider() {
        return new DefaultModelEncoderProvider();
    }

    public abstract WfsCapabilities getWfsCapabilities();

    public Map<String, String> getNamespacePrefixMap() {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("http://www.opengis.net/ows/1.1", "ows");
        returnMap.put("http://www.opengis.net/wfs/2.0", "wfs");
        returnMap.put("http://www.opengis.net/fes/2.0", "fes");
        returnMap.put("http://www.opengis.net/gml/3.2", "gml");
        returnMap.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        return returnMap;
    }

    public Map<String, String> getNamespaceSchemaMap() {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("http://www.opengis.net/ows/1.1", "http://schemas.opengis.net/ows/1.1.0/owsAll.xsd");
        returnMap.put("http://www.opengis.net/wfs/2.0", "http://schemas.opengis.net/wfs/2.0/wfs.xsd");
        returnMap.put("http://www.opengis.net/fes/2.0", "http://schemas.opengis.net/filter/2.0/filterAll.xsd");
        returnMap.put("http://www.opengis.net/gml/3.2", "http://schemas.opengis.net/gml/3.2.1/gml.xsd");
        return returnMap;
    }

}
