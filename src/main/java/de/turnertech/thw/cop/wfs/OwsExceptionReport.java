package de.turnertech.thw.cop.wfs;

import java.io.IOException;
import java.io.PrintWriter;

import de.turnertech.thw.cop.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OwsExceptionReport {
    
    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(Constants.ContentTypes.XML);
        PrintWriter writer = response.getWriter();
        writer.write(
            """
                <?xml version="1.0" ?>
                <ExceptionReport version="2.0.2" 
                xmlns="http://www.opengis.net/ows" 
                xmlns:ows="http://www.opengis.net/ows" 
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                xsi:schemaLocation="http://www.opengis.net/ows http://schemas.opengis.net/ows/1.0.0/owsExceptionReport.xsd">
                    <ows:Exception exceptionCode="OperationNotSupported" locator="request">
                        <ows:ExceptionText>Todo</ows:ExceptionText>
                    </ows:Exception>
                </ExceptionReport>
            """
        );
    }
}
