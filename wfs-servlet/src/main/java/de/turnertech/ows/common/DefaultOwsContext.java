package de.turnertech.ows.common;

import java.util.Collection;
import java.util.Map;

import de.turnertech.ows.parameter.WfsVersionValue;

public class DefaultOwsContext implements OwsContext {
    
    ModelProvider modelProvider;

    ModelEncoderProvider modelEncoderProvider;

    Collection<WfsVersionValue> supportedWfsVersions;

    WfsCapabilities wfsCapabilities;

    Map<String, String> xmlNamespacePrefixMap;

    Map<String, String> xmlNamespaceSchemaMap;

    @Override
    public ModelProvider getModelProvider() {
        return modelProvider;
    }

    void setModelProvider(ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
    }

    @Override
    public WfsCapabilities getWfsCapabilities() {
        return wfsCapabilities;
    }

    void setWfsCapabilities(WfsCapabilities wfsCapabilities) {
        this.wfsCapabilities = wfsCapabilities;
    }

    /**
     * Will generate one if not explicitely present
     */
    @Override
    public String getXmlNamespacePrefix(String namespace) {
        String prefix = xmlNamespacePrefixMap.getOrDefault(namespace, null);

        if(prefix == null) {
            int i = -1;
            do {
                prefix = "ns" + Integer.toString(++i);
            } while(xmlNamespacePrefixMap.containsValue(prefix));
            xmlNamespacePrefixMap.put(namespace, prefix);
        }

        return prefix;
    }

    Map<String, String> getXmlNamespacePrefixMap() {
        return xmlNamespacePrefixMap;
    }

    void setXmlNamespacePrefixMap(Map<String, String> xmlNamespacePrefixMap) {
        this.xmlNamespacePrefixMap = xmlNamespacePrefixMap;
    }

    @Override
    public String getXmlNamespaceSchema(String namespace) {
        return xmlNamespaceSchemaMap.getOrDefault(namespace, null);
    }

    Map<String, String> getXmlNamespaceSchemaMap() {
        return xmlNamespaceSchemaMap;
    }

    void setXmlNamespaceSchemaMap(Map<String, String> xmlNamespaceSchemaMap) {
        this.xmlNamespaceSchemaMap = xmlNamespaceSchemaMap;
    }

    @Override
    public ModelEncoderProvider getModelEncoderProvider() {
        return modelEncoderProvider;
    }

    void setModelEncoderProvider(ModelEncoderProvider modelEncoderProvider) {
        this.modelEncoderProvider = modelEncoderProvider;
    }

}
