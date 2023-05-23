package de.turnertech.thw.cop.trackers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.ErrorServlet;
import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.model.unit.Unit;
import de.turnertech.thw.cop.model.unit.UnitModel;
import de.turnertech.thw.cop.ows.ExceptionCode;
import de.turnertech.thw.cop.persistance.TrackerToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrackerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType(Constants.ContentTypes.HTML);
        response.setStatus(HttpServletResponse.SC_OK);

        XMLStreamWriter out = null;
        try {
            out = XMLOutputFactory.newInstance().createXMLStreamWriter(response.getOutputStream(), "UTF-8");
            out.writeDTD("<!DOCTYPE html>");
            out.writeStartElement("html");
            out.writeAttribute("lang", "en");

            out.writeStartElement("head");
            out.writeEmptyElement("meta");
            out.writeAttribute("charset", "UTF-8");
            out.writeEmptyElement("link");
            out.writeAttribute("rel", "icon");
            out.writeAttribute("type", "image/x-icon");
            out.writeAttribute("href", "favicon.ico");
            out.writeEmptyElement("link");
            out.writeAttribute("rel", "stylesheet");
            out.writeAttribute("href", "bootstrap/bootstrap.min.css");
            out.writeStartElement("title");
            out.writeCharacters("BOSCOP Trackers");
            out.writeEndElement();
            out.writeEndElement();

            out.writeStartElement("body");

            out.writeStartElement("form");
            out.writeAttribute("action", "/tracker");
            out.writeAttribute("method", "post");
                out.writeStartElement("label");
                    out.writeAttribute("for", "fopta");
                    out.writeCharacters("OPTA: (e.g. BUTHWND ONEB 2110)");
                out.writeEndElement();
                out.writeEmptyElement("br");

                out.writeEmptyElement("input");
                out.writeAttribute("type", "text");
                out.writeAttribute("id", "fopta");
                out.writeAttribute("name", "opta");

                out.writeEmptyElement("input");
                out.writeAttribute("type", "hidden");
                out.writeAttribute("name", "lat");
                out.writeAttribute("value", "0.0");

                out.writeEmptyElement("input");
                out.writeAttribute("type", "hidden");
                out.writeAttribute("name", "lon");
                out.writeAttribute("value", "0.0");

                out.writeEmptyElement("input");
                out.writeAttribute("type", "submit");
                out.writeAttribute("value", "Submit");
            out.writeEndElement();

            out.writeStartElement("table");
            out.writeAttribute("class", "table");
                out.writeStartElement("tr");
                    out.writeStartElement("th");
                    out.writeCharacters("OPTA");
                    out.writeEndElement();
                    out.writeStartElement("th");
                    out.writeCharacters("Key");
                    out.writeEndElement();
                out.writeEndElement();
            for (Feature tracker : UnitModel.INSTANCE.getAll()) {
                Optional<String> trackerKey = TrackerToken.getKey(((Unit)tracker).getOpta());
                if(trackerKey.isEmpty()) {
                    Logging.LOG.severe("Tracker found with no key!");
                    continue;
                }
                out.writeStartElement("tr");
                    out.writeStartElement("td");
                    out.writeCharacters(((Unit)tracker).getOpta());
                    out.writeEndElement();
                    out.writeStartElement("td");
                    out.writeCharacters(trackerKey.get());
                    out.writeEndElement();
                out.writeEndElement();
            }
            out.writeEndElement();

            out.writeEndElement();

            out.writeEndElement();
        } catch (Exception e) {
            response.sendError(500, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PROCESSING_FAILED.toString(), "GetFeatures", "XML Construction of the response failed."));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        String latString = request.getParameter("lat");
        if(latString == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.println("'lat' parameter was not present");
            return;
        }

        String lonString = request.getParameter("lon");
        if(lonString == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.println("'lon' parameter was not present");
            return;
        }

        String opta = request.getParameter("opta");
        if(opta == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.println("'opta' parameter was not present");
            return;
        }

        Unit newTracker = null;
        boolean isNewTracker = true;
        for(Feature tracker : UnitModel.INSTANCE.getAll()) {
            if(((Unit)tracker).getOpta().equals(opta)) {
                newTracker = (Unit)tracker;
                isNewTracker = false;
                break;
            }
        }

        if(newTracker == null) {
            newTracker = new Unit();
            TrackerToken.generateKey(opta);
        }
        newTracker.setLatitude(Double.valueOf(latString));
        newTracker.setLongitude(Double.valueOf(lonString));            
        newTracker.setOpta(opta);

        Optional<String> validationError = Unit.validate(newTracker);
        if(validationError.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.println(validationError.get());
            return;
        }

        if(isNewTracker) {
            UnitModel.INSTANCE.add(newTracker);
        }
        
        response.setStatus(HttpServletResponse.SC_OK);
        Logging.LOG.info("New Tracker: " + opta);

        // Not sure if this is ok? Should be I guess as long as we do not write to the response stream here
        response.sendRedirect(request.getContextPath() + "/tracker");
    }
}
