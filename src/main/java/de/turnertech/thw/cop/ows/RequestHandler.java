package de.turnertech.thw.cop.ows;

import java.io.IOException;

import de.turnertech.thw.cop.ows.api.OwsContext;
import de.turnertech.thw.cop.ows.api.OwsRequestContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RequestHandler {
    
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext, OwsRequestContext requestContext) throws ServletException, IOException;

}
