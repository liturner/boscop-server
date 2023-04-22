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
        final String wfsRequest = request.getParameter("request");
        final WfsRequestType wfsRequestType = WfsRequestType.fromString(wfsRequest);

        if(WfsRequestType.GET_CAPABILITIES.equals(wfsRequestType)) {
            WfsGetCapabilitiesRequest.doGetCapabilities(request, response);
        } else if(WfsRequestType.DESCRIBE_FEATURE_TYPE.equals(wfsRequestType)) {
            
        }
        this.log("###########");
    }

    private enum WfsRequestType {
        GET_CAPABILITIES,
        DESCRIBE_FEATURE_TYPE,
        NONE;

        public static WfsRequestType fromString(String request) {
            if(request == null) {
                return NONE;
            } else if("GetCapabilities".equalsIgnoreCase(request)) {
                return GET_CAPABILITIES;
            } else if("DescribeFeatureType".equalsIgnoreCase(request)) {
                return DESCRIBE_FEATURE_TYPE;
            } else {
                return NONE;
            }
        }
    }
}
