package de.turnertech.thw.cop.ows.parameter;

import java.util.Enumeration;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

public enum WfsRequestParameter {
    SERVICE("SERVICE"),
    VERSION("VERSION"),
    REQUEST("REQUEST"),
    TYPENAMES("TYPENAMES"),
    RESULTTYPE("RESULTTYPE"),
    BBOX("BBOX"),
    NONE("");

    private final String parameter;

    private WfsRequestParameter(final String parameter) {
        this.parameter = parameter;
    }

    /**
     * Helper function for finding the value of a parameter in a request. This ignores case!
     * 
     * @param request
     * @param parameter
     * @return
     */
    public static Optional<String> findValue(HttpServletRequest request, WfsRequestParameter parameter) {
        Enumeration<String> headerNamesEnum = request.getParameterNames();
        while(headerNamesEnum.hasMoreElements()) {
            String headerName = headerNamesEnum.nextElement();
            if(parameter.parameter.equalsIgnoreCase(headerName)) {
                return Optional.ofNullable(request.getParameter(headerName));
            }
        }
        return Optional.empty();
    }

    public static WfsRequestParameter valueOfIgnoreCase(final String parameter) {
        for(WfsRequestParameter wfsRequestParameter : WfsRequestParameter.values()) {
            if(wfsRequestParameter.parameter.equalsIgnoreCase(parameter)) {
                return wfsRequestParameter;
            }
        }
        return NONE;
    }
}
