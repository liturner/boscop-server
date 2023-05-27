package de.turnertech.ows.parameter;

public enum ResultType {
    
    RESULTS("results"),
    HITS("hits"),
    NONE("");

    private final String parameter;

    private ResultType(final String parameter) {
        this.parameter = parameter;
    }

    public static ResultType valueOfIgnoreCase(final String parameter) {
        for(ResultType resultType : ResultType.values()) {
            if(resultType.parameter.equalsIgnoreCase(parameter)) {
                return resultType;
            }
        }
        return NONE;
    }

    @Override
    public String toString() {
        return parameter;
    }

}
