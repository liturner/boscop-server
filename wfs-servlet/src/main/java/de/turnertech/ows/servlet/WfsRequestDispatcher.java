package de.turnertech.ows.servlet;

import java.io.IOException;
import java.util.Optional;

import de.turnertech.ows.common.ExceptionCode;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.OwsRequestContext;
import de.turnertech.ows.common.RequestHandler;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.parameter.WfsRequestParameter;
import de.turnertech.ows.parameter.WfsRequestValue;
import de.turnertech.ows.parameter.WfsVersionValue;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class WfsRequestDispatcher implements RequestHandler {

    private final WfsDescribeFeatureTypeRequest describeFeatureTypeRequestHandler;
    private final WfsGetCapabilitiesRequest getCapabilitiesRequestHandler;
    private final WfsGetFeatureRequest getFeatureRequestHandler;
    private final WfsTransactionRequest transactionRequestHandler;

    public WfsRequestDispatcher() {
        describeFeatureTypeRequestHandler = new WfsDescribeFeatureTypeRequest();
        getCapabilitiesRequestHandler = new WfsGetCapabilitiesRequest();
        getFeatureRequestHandler = new WfsGetFeatureRequest();
        transactionRequestHandler = new WfsTransactionRequest();
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext, OwsRequestContext requestContext) throws ServletException, IOException {
        final Optional<String> wfsRequest = WfsRequestParameter.findValue(request, WfsRequestParameter.REQUEST);
        if(wfsRequest.isEmpty()) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), WfsRequestParameter.REQUEST.toString(), "No REQUEST parameter supplied"));
            return;
        }
        final WfsRequestValue wfsRequestType = WfsRequestValue.valueOfIgnoreCase(wfsRequest.get());
        if(wfsRequestType == null || wfsRequestType == WfsRequestValue.NONE) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), WfsRequestParameter.REQUEST.toString(), "Invalid REQUEST parameter supplied: " + wfsRequest.get()));
            return;
        }

        if(WfsRequestValue.GET_CAPABILITIES.equals(wfsRequestType)) {
            getCapabilitiesRequestHandler.handleRequest(request, response, owsContext, requestContext);
            return;
        }
        
        /**
         * Get and set the VERSION
         */
        final Optional<String> wfsVersion = WfsRequestParameter.findValue(request, WfsRequestParameter.VERSION);
        if(wfsVersion.isEmpty()) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), WfsRequestParameter.VERSION.toString(), "No VERSION parameter supplied"));
            return;
        }
        requestContext.setOwsVersion(WfsVersionValue.valueOfIgnoreCase(wfsVersion.get()));
        if(requestContext.getOwsVersion() == null) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), WfsRequestParameter.VERSION.toString(), "Invalid VERSION parameter supplied: " + wfsVersion.get()));
            return;
        }
        if(!owsContext.getWfsCapabilities().getServiceTypeVersions().contains(requestContext.getOwsVersion())) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), WfsRequestParameter.VERSION.toString(), "Unsupported VERSION parameter supplied: " + wfsVersion.get()));
            return;
        }

        if(WfsRequestValue.TRANSACTION.equals(wfsRequestType)) {
            transactionRequestHandler.handleRequest(request, response, owsContext, requestContext);
            return;
        }

        /**
         * Get and set the TYPENAMES
         */
        final String typenamesValue = WfsRequestParameter.findValue(request, WfsRequestParameter.TYPENAMES).orElse(null);
        if(typenamesValue == null || typenamesValue.trim().equals("")) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.INVALID_PARAMETER_VALUE.toString(), WfsRequestParameter.TYPENAMES.toString(), "No value supplied"));
            return;
        }
        String[] typenames = typenamesValue.split(",");
        for(String typename : typenames) {
            String[] typenameParts = typename.split(":");
            boolean containsType = false;
            for(FeatureType featureType : owsContext.getWfsCapabilities().getFeatureTypes()) {
                if(featureType.getName().equals(typenameParts[1]) && owsContext.getXmlNamespacePrefix(featureType.getNamespace()).equals(typenameParts[0])) {
                    containsType = true;
                    requestContext.getFeatureTypes().add(featureType);
                    break;
                }
            }
            if(!containsType) {
                response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.INVALID_PARAMETER_VALUE.toString(), WfsRequestParameter.TYPENAMES.toString(), "The value \"" + typename + "\" is not a known typeName"));
                return;
            }
        }

        if(WfsRequestValue.DESCRIBE_FEATURE_TYPE.equals(wfsRequestType)) {
            describeFeatureTypeRequestHandler.handleRequest(request, response, owsContext, requestContext);
            return;
        }

        final String srsnameValue = WfsRequestParameter.findValue(request, WfsRequestParameter.SRSNAME).orElse(null);
        if(srsnameValue != null) {
            SpatialReferenceSystemRepresentation requestedSrs = SpatialReferenceSystemRepresentation.from(srsnameValue);
            if(requestedSrs == null) {
                response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.INVALID_PARAMETER_VALUE.toString(), WfsRequestParameter.SRSNAME.toString(), "Requested SRS is not known to the system."));
                return;
            }
            requestContext.setRequestedSrs(requestedSrs);
        }

        if(WfsRequestValue.GET_FEATURE.equals(wfsRequestType)) {
            getFeatureRequestHandler.handleRequest(request, response, owsContext, requestContext);
            return;
        }

    }

    
    
}
