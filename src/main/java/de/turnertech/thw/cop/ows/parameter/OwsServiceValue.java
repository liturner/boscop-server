package de.turnertech.thw.cop.ows.parameter;

public enum OwsServiceValue {

    WFS("WFS");

    private final String parameter;

    private OwsServiceValue(final String parameter) {
        this.parameter = parameter;
    }

    public static OwsServiceValue valueOfIgnoreCase(final String parameter) {
        for(OwsServiceValue wfsRequestValue : OwsServiceValue.values()) {
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
