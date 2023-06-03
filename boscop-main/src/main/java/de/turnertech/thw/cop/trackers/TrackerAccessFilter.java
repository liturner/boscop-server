package de.turnertech.thw.cop.trackers;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrackerAccessFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String apiKey = httpRequest.getHeader("X-API-Key");
        final String contentType = httpRequest.getContentType();
        final String opta = httpRequest.getParameter("opta");

        if(!"PUT".equals(httpRequest.getMethod())) {
            httpResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method must be PUT");
        } else if(apiKey == null) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "X-API-Key header must be supplied");
        } else if(contentType == null) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Content-Type header must be supplied");
        } else if(!contentType.toLowerCase().startsWith("application/x-www-form-urlencoded")) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Content-Type must be 'application/x-www-form-urlencoded'");
        } else if(opta == null) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else if(TrackerToken.isValid(opta, apiKey)) {  
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }  
    }
}