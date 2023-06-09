package de.turnertech.ows.parameter;

public enum WfsVersionValue {

    V2_0_0("2.0.0"),
    V2_0_2("2.0.2");

    private final String parameter;

    private WfsVersionValue(final String parameter) {
        this.parameter = parameter;
    }

    public static WfsVersionValue valueOfIgnoreCase(final String parameter) {
        for(WfsVersionValue wfsRequestValue : WfsVersionValue.values()) {
            if(wfsRequestValue.parameter.equalsIgnoreCase(parameter)) {
                return wfsRequestValue;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return parameter;
    }
}
