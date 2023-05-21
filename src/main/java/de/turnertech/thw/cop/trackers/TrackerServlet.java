package de.turnertech.thw.cop.trackers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.ows.model.unit.Unit;
import de.turnertech.thw.cop.ows.model.unit.UnitModel;
import de.turnertech.thw.cop.persistance.TrackerToken;
import de.turnertech.thw.cop.util.DataObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrackerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = response.getWriter();
        writer.println("{\"trackers\":[");
        for (DataObject tracker : UnitModel.INSTANCE.getAll()) {
            Optional<String> trackerKey = TrackerToken.getKey(((Unit)tracker).getOpta());
            if(trackerKey.isEmpty()) {
                Logging.LOG.severe("Tracker found with no key!");
                continue;
            }

            writer.println("{");
            writer.println("\"opta\":\"" + ((Unit)tracker).getOpta() + "\",");
            writer.println("\"key\":\"" + trackerKey.get() + "\"");
            writer.println("},");
        }
        writer.println("]}");
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
        for(DataObject tracker : UnitModel.INSTANCE.getAll()) {
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
    }
}
