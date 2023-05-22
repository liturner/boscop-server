package de.turnertech.thw.cop.ows;

import java.io.IOException;

import de.turnertech.thw.cop.ows.api.OwsContext;
import de.turnertech.thw.cop.ows.api.OwsContextFactory;
import de.turnertech.thw.cop.ows.parameter.WfsRequestParameter;
import de.turnertech.thw.cop.ows.parameter.WfsRequestValue;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsServlet extends HttpServlet {
    
    public static final String OWS_CONTEXT_FACTORY_KEY = "ows.context.factory.class";

    private OwsContext owsContext;

    private OwsServiceDispatcher owsServiceDispatcher;

    @Override
    public void init() throws ServletException {
        String owsContextFactoryClassString = super.getInitParameter(OWS_CONTEXT_FACTORY_KEY);
        if(owsContextFactoryClassString == null || "".equals(owsContextFactoryClassString)) {
            throw new ServletException("ows.context.factory.class init parameter was not set.");
        }

        try {
            Class<?> owsContextFactoryClass = Class.forName(owsContextFactoryClassString);
            OwsContextFactory owsContextFactory = (OwsContextFactory)owsContextFactoryClass.getDeclaredConstructor().newInstance();
            owsContext = owsContextFactory.createOwsContext();
        } catch (ClassNotFoundException e) {
            throw new ServletException("Could not find class: " + owsContextFactoryClassString, e);
        } catch (Exception e) {
            throw new ServletException("Could not intantiat OwsContextFactory", e);
        }

        owsServiceDispatcher = new OwsServiceDispatcher();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        owsServiceDispatcher.handleRequest(request, response, owsContext);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        owsServiceDispatcher.handleRequest(request, response, owsContext);

        final String wfsRequest = WfsRequestParameter.findValue(request, WfsRequestParameter.REQUEST).get();
        final WfsRequestValue wfsRequestType = WfsRequestValue.valueOfIgnoreCase(wfsRequest);

        if(WfsRequestValue.TRANSACTION.equals(wfsRequestType)) {
            WfsTransactionRequest.doPost(request, response);
        }
    }
}
