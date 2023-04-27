package de.turnertech.thw.cop.ows;

import java.io.IOException;

import de.turnertech.thw.cop.ErrorServlet;
import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.ows.model.area.AreaModel;
import de.turnertech.thw.cop.ows.model.hazard.HazardModel;
import de.turnertech.thw.cop.ows.model.unit.UnitModel;
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
        } else if(wfsRequestValue == WfsRequestValue.GET_FEATURE) {
            doGetFeatureFilter(httpRequest, httpResponse, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * See OGC 09-025r2 Table 8.
     * 
     * TYPENAMES is mandatory, all else is optional.
     * 
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
    */
    private void doGetFeatureFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String typenamesParameterString = WfsRequestParameter.findValue(request, WfsRequestParameter.TYPENAMES).orElse(null);

        String typenamesValue = WfsRequestParameter.findValue(request, WfsRequestParameter.TYPENAMES).orElse(null);
        if(typenamesValue == null || typenamesValue.trim().equals("")) {
            response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.INVALID_PARAMETER_VALUE.toString(), WfsRequestParameter.TYPENAMES.toString(), "No value supplied"));
            return;
        }

        String[] typenames = typenamesValue.split(",");
        for(String typename : typenames) {
            if(!(AreaModel.TYPENAME.equalsIgnoreCase(typename) || UnitModel.TYPENAME.equalsIgnoreCase(typename) || HazardModel.TYPENAME.equalsIgnoreCase(typename))) {
                response.sendError(400, ErrorServlet.encodeMessage(ExceptionCode.INVALID_PARAMETER_VALUE.toString(), WfsRequestParameter.TYPENAMES.toString(), "The value \"" + typename + "\" is not a known typeName"));
                return;
            }
        }

        if(typenamesParameterString == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ErrorServlet.encodeMessage(ExceptionCode.MISSING_PARAMETER_VALUE.toString(), WfsRequestParameter.TYPENAMES.toString()));
            return;
        }

        Logging.LOG.info("request=GetFeature&typeNames="+typenamesValue);

        chain.doFilter(request, response);

    }
}