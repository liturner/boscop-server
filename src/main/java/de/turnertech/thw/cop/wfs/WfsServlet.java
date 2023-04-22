package de.turnertech.thw.cop.wfs;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.log("###########");
        this.log(request.getRequestURL().toString() + "?" + request.getQueryString());

        final String wfsRequest = WfsRequestParameter.findValue(request, WfsRequestParameter.REQUEST).get();
        final WfsRequestType wfsRequestType = WfsRequestType.valueOfIgnoreCase(wfsRequest);

        if(WfsRequestType.GET_CAPABILITIES.equals(wfsRequestType)) {
            WfsGetCapabilitiesRequest.doGet(request, response);
        } else if(WfsRequestType.DESCRIBE_FEATURE_TYPE.equals(wfsRequestType)) {
            WfsDescribeFeatureTypeRequest.doGet(request, response);
        } else if(WfsRequestType.GET_FEATURE.equals(wfsRequestType)) {
            WfsGetFeatureRequest.doGet(request, response);
        }
        this.log("###########");
    }

    private enum WfsRequestType {
        GET_CAPABILITIES("GetCapabilities"),
        DESCRIBE_FEATURE_TYPE("DescribeFeatureType"),
        GET_FEATURE("GetFeature"),
        NONE("");

        private final String parameter;

        private WfsRequestType(final String parameter) {
            this.parameter = parameter;
        }

        public static WfsRequestType valueOfIgnoreCase(final String parameter) {
            for(WfsRequestType wfsRequestType : WfsRequestType.values()) {
                if(wfsRequestType.parameter.equalsIgnoreCase(parameter)) {
                    return wfsRequestType;
                }
            }
            return NONE;
        }
    }
}
