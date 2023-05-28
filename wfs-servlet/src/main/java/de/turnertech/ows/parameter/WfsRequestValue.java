package de.turnertech.ows.parameter;

public enum WfsRequestValue {
    GET_CAPABILITIES("GetCapabilities"),
    GET_FEATURE("GetFeature"),
    DESCRIBE_FEATURE_TYPE("DescribeFeatureType"),
    TRANSACTION("Transaction"),
    NONE("");

    private final String parameter;

    private WfsRequestValue(final String parameter) {
        this.parameter = parameter;
    }

    public static WfsRequestValue valueOfIgnoreCase(final String parameter) {
        for(WfsRequestValue wfsRequestValue : WfsRequestValue.values()) {
            if(wfsRequestValue.parameter.equalsIgnoreCase(parameter)) {
                return wfsRequestValue;
            }
        }
        return NONE;
    }
}
