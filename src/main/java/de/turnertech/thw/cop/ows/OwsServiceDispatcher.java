package de.turnertech.thw.cop.ows;

import java.io.IOException;
import java.util.Optional;

import de.turnertech.thw.cop.ErrorServlet;
import de.turnertech.thw.cop.ows.api.OwsContext;
import de.turnertech.thw.cop.ows.parameter.OwsServiceValue;
import de.turnertech.thw.cop.ows.parameter.WfsRequestParameter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This is a layer to help dispatch between varying types of OWS service on the same endpoint.
 */
class OwsServiceDispatcher implements RequestHandler {

    private RequestHandler wfRequestHandler = new WfsRequestDispatcher();

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext) throws ServletException, IOException {
        final Optional<String> wfsService = WfsRequestParameter.findValue(request, WfsRequestParameter.SERVICE);
        if(wfsService.isEmpty()) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), "SERVICE", "No SERVICE parameter supplied"));
            return;
        }
        
        final OwsServiceValue wfsRequestType = OwsServiceValue.valueOfIgnoreCase(wfsService.get());
        if(wfsRequestType == null) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), "SERVICE", "Invalid SERVICE parameter supplied: " + wfsService.get()));
            return;
        }

        if(wfsRequestType == OwsServiceValue.WFS) {
            wfRequestHandler.handleRequest(request, response, owsContext);
        }

    }
    
}
