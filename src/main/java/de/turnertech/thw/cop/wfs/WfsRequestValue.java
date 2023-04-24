package de.turnertech.thw.cop.wfs;

public enum WfsRequestValue {
    GET_CAPABILITIES("GetCapabilities"),
    GET_FEATURE("GetFeature"),
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
