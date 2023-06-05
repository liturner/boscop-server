package de.turnertech.thw.cop.trackers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.ExceptionCode;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.gml.Point;
import de.turnertech.ows.servlet.ErrorServlet;
import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.model.UnitModel;
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
            out.writeEmptyElement("link");
            out.writeAttribute("rel", "stylesheet");
            out.writeAttribute("href", "boscop/style.css");
            out.writeStartElement("title");
            out.writeCharacters("BOSCOP Trackers");
            out.writeEndElement();
            out.writeEndElement();

            out.writeStartElement("body");

            out.writeStartElement("nav");
                out.writeStartElement("ul");
                    out.writeStartElement("li");
                        out.writeStartElement("a");
                            out.writeAttribute("href", "/");
                            out.writeCharacters("Map");
                        out.writeEndElement();
                    out.writeEndElement();
                    out.writeStartElement("li");
                        out.writeStartElement("a");
                            out.writeAttribute("href", "tracker");
                            out.writeAttribute("class", "active");
                            out.writeCharacters("Trackers");
                        out.writeEndElement();
                    out.writeEndElement();
                    out.writeStartElement("li");
                        out.writeStartElement("a");
                            out.writeAttribute("href", "api");
                            out.writeCharacters("API");
                        out.writeEndElement();
                    out.writeEndElement();
                out.writeEndElement();
            out.writeEndElement();

            out.writeStartElement("main");
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
                    out.writeAttribute("id", "tracker-table");
                    out.writeAttribute("class", "table");
                    out.writeStartElement("tr");
                        out.writeStartElement("th");
                        out.writeCharacters("OPTA");
                        out.writeEndElement();
                        out.writeStartElement("th");
                        out.writeCharacters("Key");
                        out.writeEndElement();
                    out.writeEndElement();
                for (Entry<Object, Object> trackerKey : TrackerToken.entrySet()) {
                    out.writeStartElement("tr");
                        out.writeStartElement("td");
                        out.writeCharacters(trackerKey.getKey().toString());
                        out.writeEndElement();
                        out.writeStartElement("td");
                        out.writeCharacters(trackerKey.getValue().toString());
                        out.writeEndElement();
                    out.writeEndElement();
                }
                out.writeEndElement();
                out.writeEndElement();

                out.writeStartElement("footer");
                out.writeEndElement();

                out.writeEndElement(); // body

            out.writeEndElement(); // html
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

        IFeature newTracker = null;
        boolean isNewTracker = true;
        for(IFeature tracker : UnitModel.INSTANCE.getAll()) {
            if(opta.equals(tracker.getPropertyValue(UnitModel.OPTA_FIELD))) {
                newTracker = tracker;
                isNewTracker = false;
                break;
            }
        }

        if(newTracker == null) {
            newTracker = UnitModel.INSTANCE.getFeatureType().createInstance();
            TrackerToken.generateKey(opta);
            TrackerToken.save();
        }

        newTracker.setPropertyValue(UnitModel.ID_FIELD, opta);
        newTracker.setPropertyValue(UnitModel.OPTA_FIELD, opta);
        newTracker.setPropertyValue(UnitModel.GEOMETRY_FIELD, new Point(Double.valueOf(lonString), Double.valueOf(latString)));

        if(isNewTracker) {
            UnitModel.INSTANCE.add(newTracker);
        }
        
        response.setStatus(HttpServletResponse.SC_OK);
        Logging.LOG.info("New Tracker: " + opta);

        // Not sure if this is ok? Should be I guess as long as we do not write to the response stream here
        response.sendRedirect(request.getContextPath() + "/tracker");
    }
}
