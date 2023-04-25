package de.turnertech.thw.cop.wfs;

import java.io.BufferedReader;
import java.io.IOException;

import de.turnertech.thw.cop.Logging;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsTransactionRequest {
    
    private WfsTransactionRequest() {
        
    }

    public static void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String resultTypeString = WfsRequestParameter.findValue(request, WfsRequestParameter.RESULTTYPE).orElse(ResultType.RESULTS.toString());
        final ResultType resultType = ResultType.valueOfIgnoreCase(resultTypeString);
        
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
        }
        String data = stringBuilder.toString();
        Logging.LOG.info(data);
    }
}
