package de.turnertech.thw.cop.trackers;

import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.gml.Point;
import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.model.UnitModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/tracker/update")
public class TrackerSubServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Optional<String> latString = Optional.ofNullable(request.getParameter("lat"));
        final Optional<String> lonString = Optional.ofNullable(request.getParameter("lon"));

        final String opta = request.getParameter(UnitModel.OPTA_FIELD);
        if(opta == null) {
            // Server Error, because the filter should already have prevented us getting here.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "opta parameter must be present.");
            return;
        }

        Optional<String> apiKey = TrackerToken.getKey(opta);
        if(apiKey.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "opta is not registered.");
            return;
        }

        IFeature existingTracker = null;
        for(IFeature tracker : UnitModel.INSTANCE.getAll()) {
            if(opta.equals(tracker.getPropertyValue(UnitModel.OPTA_FIELD))) {
                existingTracker = tracker;
                break;
            }
        }
        if(existingTracker == null) {
            // This can occur after a restart. There is a stored Key, but no active tracker.
            existingTracker = UnitModel.INSTANCE.getFeatureType().createInstance();
            existingTracker.setPropertyValue(UnitModel.ID_FIELD, opta);
            existingTracker.setPropertyValue(UnitModel.OPTA_FIELD, opta);
            existingTracker.setPropertyValue(UnitModel.GEOMETRY_FIELD, new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
            UnitModel.INSTANCE.add(existingTracker);
        }

        Point trackerLocation = (Point)existingTracker.getPropertyValue(UnitModel.GEOMETRY_FIELD);

        if(latString.isPresent()) {
            trackerLocation.setY(Double.valueOf(latString.get()));
            existingTracker.setPropertyValue(UnitModel.GEOMETRY_FIELD, trackerLocation);
        }
        if(lonString.isPresent()) {
            trackerLocation.setX(Double.valueOf(lonString.get()));
            existingTracker.setPropertyValue(UnitModel.GEOMETRY_FIELD, trackerLocation);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        Logging.LOG.fine(() -> "TrackerSubServlet: " + opta);
    }
}
