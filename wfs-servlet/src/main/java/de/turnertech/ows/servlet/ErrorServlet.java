package de.turnertech.ows.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import de.turnertech.ows.common.ExceptionCode;
import de.turnertech.ows.common.RequestHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * We use OWS for everything, because "why not?"
 */
public class ErrorServlet extends HttpServlet {
 
    // Method to handle GET method request.
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String message = (String) request.getAttribute("jakarta.servlet.error.message");
        final Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        ExceptionCode owsExceptionCode = ExceptionCode.NO_APPLICABLE_CODE;
        Optional<String> locator = Optional.empty();
        Optional<String> exceptionText = Optional.empty();

        if(message != null) {
            String[] exceptionCodeStrings = message.split(":", 3);
            owsExceptionCode = ExceptionCode.valueOfIgnoreCase(exceptionCodeStrings[0]);
            
            if(exceptionCodeStrings.length > 1) {
                locator = Optional.ofNullable(exceptionCodeStrings[1]);
            }

            if(exceptionCodeStrings.length > 2) {
                exceptionText = Optional.ofNullable(exceptionCodeStrings[2]);
            }
        } else {
            // Analyze the servlet exception
            // Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
            // Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
            // String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");
        }

        // Set response content type
        response.setContentType(RequestHandler.CONTENT_XML);
        if(statusCode != null) {
            response.setStatus(statusCode);
        }
 
        PrintWriter out = response.getWriter();
        out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.print("<ExceptionReport version=\"2.0.2\" lang=\"en-GB\" xmlns=\"http://www.opengis.net/ows/2.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/ows/2.0 https://schemas.opengis.net/ows/2.0/owsAll.xsd\">");
        out.print("<Exception exceptionCode=\"");
        out.print(owsExceptionCode.toString());
        out.print("\"");
        if(locator.isPresent()) {
            out.print(" locator=\"" + locator.get() + "\"");
        }
        
        if(exceptionText.isPresent()) {
            out.print(">");
            out.print("<ExceptionText>");
            out.print(exceptionText.get());
            out.print("</ExceptionText>");
            out.print("</Exception>");
        } else {
            out.print("/>");
        }
        out.print("</ExceptionReport>");
    }
    
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
       
        doGet(request, response);
    }

    public static String encodeMessage(String exceptionCode, String locator) {
        return String.join(":", exceptionCode, locator);
    }

    public static String encodeMessage(String exceptionCode, String locator, String message) {
        return String.join(":", exceptionCode, locator, message);
    }
}