package de.turnertech.thw.cop.ows;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.log("###########");
        this.log(request.getRequestURL().toString() + "?" + request.getQueryString());

        final String wfsRequest = WfsRequestParameter.findValue(request, WfsRequestParameter.REQUEST).get();
        final WfsRequestValue wfsRequestType = WfsRequestValue.valueOfIgnoreCase(wfsRequest);

        if(WfsRequestValue.GET_CAPABILITIES.equals(wfsRequestType)) {
            WfsGetCapabilitiesRequest.doGet(request, response);
        } else if(WfsRequestValue.DESCRIBE_FEATURE_TYPE.equals(wfsRequestType)) {
            WfsDescribeFeatureTypeRequest.doGet(request, response);
        } else if(WfsRequestValue.GET_FEATURE.equals(wfsRequestType)) {
            WfsGetFeatureRequest.doGet(request, response);
        }
        this.log("###########");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.log("-----------");
        this.log(request.getRequestURL().toString() + "?" + request.getQueryString());

        final String wfsRequest = WfsRequestParameter.findValue(request, WfsRequestParameter.REQUEST).get();
        final WfsRequestValue wfsRequestType = WfsRequestValue.valueOfIgnoreCase(wfsRequest);

        if(WfsRequestValue.TRANSACTION.equals(wfsRequestType)) {
            WfsTransactionRequest.doPost(request, response);
        }

        this.log("-----------");
    }
}
