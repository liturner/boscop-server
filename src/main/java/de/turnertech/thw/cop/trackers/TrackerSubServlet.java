package de.turnertech.thw.cop.trackers;

import java.io.IOException;
import java.util.Optional;

import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.gml.IFeature;
import de.turnertech.thw.cop.model.unit.Unit;
import de.turnertech.thw.cop.model.unit.UnitModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrackerSubServlet extends HttpServlet {
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Optional<String> latString = Optional.ofNullable(request.getParameter("lat"));
        final Optional<String> lonString = Optional.ofNullable(request.getParameter("lon"));

        final String opta = request.getParameter("opta");
        if(opta == null) {
            // Server Error, because the filter should already have prevented us getting here.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "opta parameter must be present.");
            return;
        }

        Unit existingTracker = null;
        for(IFeature tracker : UnitModel.INSTANCE.getAll()) {
            if(((Unit)tracker).getOpta().equals(opta)) {
                existingTracker = (Unit)tracker;
                break;
            }
        }

        if(existingTracker == null) {
            // Server Error, because the filter should already have prevented us getting here.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "opta is not registered as a tracker.");
            return;
        }

        if(latString.isPresent()) {
            existingTracker.setLatitude(Double.valueOf(latString.get()));
        }
        if(lonString.isPresent()) {
            existingTracker.setLongitude(Double.valueOf(lonString.get()));
        }
        
        response.setStatus(HttpServletResponse.SC_OK);
        Logging.LOG.fine("Update Tracker (API): " + opta);
    }
}
