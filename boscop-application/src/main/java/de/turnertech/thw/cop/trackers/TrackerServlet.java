package de.turnertech.thw.cop.trackers;

import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.gml.Point;
import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.model.UnitModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TrackerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
