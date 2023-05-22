package de.turnertech.thw.cop.ows;

import java.io.IOException;

import de.turnertech.thw.cop.ows.api.OwsContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RequestHandler {
    
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext) throws ServletException, IOException;

}
