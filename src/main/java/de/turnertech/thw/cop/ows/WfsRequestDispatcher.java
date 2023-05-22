package de.turnertech.thw.cop.ows;

import java.io.IOException;
import java.util.Optional;

import de.turnertech.thw.cop.ErrorServlet;
import de.turnertech.thw.cop.ows.api.OwsContext;
import de.turnertech.thw.cop.ows.parameter.WfsRequestParameter;
import de.turnertech.thw.cop.ows.parameter.WfsRequestValue;
import de.turnertech.thw.cop.ows.parameter.WfsVersionValue;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class WfsRequestDispatcher implements RequestHandler {

    private final WfsDescribeFeatureTypeRequest describeFeatureTypeRequestHandler;

    public WfsRequestDispatcher() {
        describeFeatureTypeRequestHandler = new WfsDescribeFeatureTypeRequest();
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext) throws ServletException, IOException {
        final Optional<String> wfsRequest = WfsRequestParameter.findValue(request, WfsRequestParameter.REQUEST);
        if(wfsRequest.isEmpty()) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), "REQUEST", "No REQUEST parameter supplied"));
            return;
        }
        final WfsRequestValue wfsRequestType = WfsRequestValue.valueOfIgnoreCase(wfsRequest.get());
        if(wfsRequestType == null || wfsRequestType == WfsRequestValue.NONE) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), "REQUEST", "Invalid REQUEST parameter supplied: " + wfsRequest.get()));
            return;
        }

        if(WfsRequestValue.GET_CAPABILITIES.equals(wfsRequestType)) {
            WfsGetCapabilitiesRequest.doGet(request, response);
            return;
        }
        
        final Optional<String> wfsVersion = WfsRequestParameter.findValue(request, WfsRequestParameter.VERSION);
        if(wfsVersion.isEmpty()) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), "VERSION", "No VERSION parameter supplied"));
            return;
        }
        final WfsVersionValue wfsVersionValue = WfsVersionValue.valueOfIgnoreCase(wfsVersion.get());
        if(wfsVersionValue == null) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), "VERSION", "Invalid VERSION parameter supplied: " + wfsVersion.get()));
            return;
        }
        if(!owsContext.getSupportedWfsVersions().contains(wfsVersionValue)) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), "VERSION", "Unsupported VERSION parameter supplied: " + wfsVersion.get()));
            return;
        }

        if(WfsRequestValue.DESCRIBE_FEATURE_TYPE.equals(wfsRequestType)) {
            describeFeatureTypeRequestHandler.handleRequest(request, response, owsContext);
            return;
        } else if(WfsRequestValue.GET_FEATURE.equals(wfsRequestType)) {
            WfsGetFeatureRequest.doGet(request, response);
            return;
        }
    }

    
    
}
