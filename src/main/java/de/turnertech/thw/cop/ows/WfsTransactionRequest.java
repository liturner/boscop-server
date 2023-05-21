package de.turnertech.thw.cop.ows;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.ows.model.area.AreaDecoder;
import de.turnertech.thw.cop.ows.model.area.AreaModel;
import de.turnertech.thw.cop.ows.model.hazard.HazardDecoder;
import de.turnertech.thw.cop.ows.model.hazard.HazardModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsTransactionRequest {
    
    private WfsTransactionRequest() {
        
    }

    public static void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String resultTypeString = WfsRequestParameter.findValue(request, WfsRequestParameter.RESULTTYPE).orElse(ResultType.RESULTS.toString());
        final ResultType resultType = ResultType.valueOfIgnoreCase(resultTypeString);
        
        /**
        SAXParserFactory factory = SAXParserFactory.newInstance();
        GmlDecoder decoder = new GmlDecoder();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(request.getInputStream(), decoder);
        } catch (Exception e) {
            Logging.LOG.severe("Could not decode GML from Transaction");
        }
         */

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(request.getInputStream());
            Element root = document.getDocumentElement();

            NodeList deleteEntries = root.getElementsByTagName("Delete");
            NodeList insertEntries = root.getElementsByTagName("Insert");

            

            AreaModel.INSTANCE.addAll(AreaDecoder.getAreas(root));
            HazardModel.INSTANCE.addAll(HazardDecoder.getHazards(root));
        } catch (Exception e) {
            Logging.LOG.severe("Could not decode GML from Transaction");
        }
    }
}
