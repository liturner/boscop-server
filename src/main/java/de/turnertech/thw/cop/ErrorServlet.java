package de.turnertech.thw.cop;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * We use OWS for everything, because "why not?"
 */
public class ErrorServlet extends HttpServlet {
 
    // Method to handle GET method request.
    public void doGet(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {
          
       // Analyze the servlet exception       
       Throwable throwable = (Throwable)
       request.getAttribute("javax.servlet.error.exception");
       Integer statusCode = (Integer)
       request.getAttribute("javax.servlet.error.status_code");         

       String requestUri = (String)
       request.getAttribute("javax.servlet.error.request_uri");
       
       if (requestUri == null) {
          requestUri = "Unknown";
       }
 
       // Set response content type
       response.setContentType(Constants.ContentTypes.XML);
 
       PrintWriter out = response.getWriter();
       out.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
       out.print("<ows:ExceptionReport version=\"2.0.2\" xmlns:ows=\"http://www.opengis.net/ows\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/ows http://schemas.opengis.net/ows/1.0.0/owsExceptionReport.xsd\">");
       out.print("<ows:Exception exceptionCode=\"OperationNotSupported\" locator=\"request\"><ows:ExceptionText>");
       out.print("ToDo");
       out.print("</ows:ExceptionText></ows:Exception></ows:ExceptionReport>");
    }
    
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {
       
       doGet(request, response);
    }
 }