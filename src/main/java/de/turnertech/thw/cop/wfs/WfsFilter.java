package de.turnertech.thw.cop.wfs;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        final String contentType = httpRequest.getContentType();
        final String wfsRequestString = WfsRequestParameter.findValue(httpRequest, WfsRequestParameter.REQUEST).orElse(WfsRequestParameter.NONE.toString());
        WfsRequestValue wfsRequestValue = WfsRequestValue.valueOfIgnoreCase(wfsRequestString);

        if(wfsRequestValue == WfsRequestValue.NONE) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ExceptionCode.OPERATION_NOT_SUPPORTED.toString());
        } else {
            chain.doFilter(request, response);
        }
    }
}