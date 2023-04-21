package de.turnertech.thw.cop;

import java.io.IOException;
import java.io.PrintWriter;

import de.turnertech.thw.cop.trackers.Tracker;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CopServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * See RFC 7946
     * https://datatracker.ietf.org/doc/html/rfc7946
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = response.getWriter();
        writer.println("{");
        writer.println("\"type\": \"FeatureCollection\",");
        writer.println("\"features\": [");

        for (int i = 0; i < Tracker.TRACKERS.size(); ++i) {
            Tracker tracker = Tracker.TRACKERS.get(i);
            writer.println("{\"type\": \"Feature\",");
            writer.println("\"geometry\": {");
            writer.println("\"type\": \"Point\",");
            writer.println("\"coordinates\": [" + tracker.getLongitude() + ", " + tracker.getLatitude() + "]");
            writer.println("},");
            writer.println("\"properties\": {");
            writer.println("\"OPTA\": \"" + tracker.getOpta() + "\"");
            writer.println("}");
            if(i < Tracker.TRACKERS.size() - 1) {
                writer.println("},");
            } else {
                writer.println("}");
            }
        }

        writer.println("]");
        writer.println("}");
    }
}
