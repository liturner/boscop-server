package de.turnertech.thw.cop.wfs;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.trackers.Tracker;
import de.turnertech.thw.cop.util.BoundingBox;
import de.turnertech.thw.cop.wfs.model.area.Area;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsGetFeatureRequest {
    
    private WfsGetFeatureRequest() {
        
    }

    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String resultTypeString = WfsRequestParameter.findValue(request, WfsRequestParameter.RESULTTYPE).orElse(ResultType.RESULTS.toString());
        final ResultType resultType = ResultType.valueOfIgnoreCase(resultTypeString);
        if(ResultType.HITS == resultType) {
            doGetHits(request, response);
        } else if (ResultType.RESULTS == resultType) {
            doGetResults(request, response);
        }
    }

    /**
     * @see OGC 09-025r2 B3.18
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private static void doGetHits(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(Constants.ContentTypes.XML);
        PrintWriter writer = response.getWriter();
        writer.write("<?xml version=\"1.0\"?>\n");
        writer.write("<wfs:FeatureCollection timeStamp=\"");
        writer.write(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        writer.write("\" numberMatched=\"");
        writer.write(String.valueOf(Tracker.TRACKERS.size()));
        writer.write("\" numberReturned=\"0\" xmlns:wfs=\"http://www.opengis.net/wfs/2.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0/wfs.xsd\"/>");        
    }

    private static void doGetResults(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(Constants.ContentTypes.XML);
        Optional<BoundingBox> boundingBox = BoundingBox.from(Tracker.TRACKERS);
        
        PrintWriter writer = response.getWriter();
        writer.write("<?xml version=\"1.0\"?>\n");
        writer.write("<gml:FeatureCollection timeStamp=\"");
        writer.write(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        writer.write("\" numberMatched=\"");
        writer.write(String.valueOf(Tracker.TRACKERS.size()));
        writer.write("\" numberReturned=\"");
        writer.write(String.valueOf(Tracker.TRACKERS.size()));
        writer.write("\" xmlns:boscop=\"urn:ns:de:turnertech:boscop\" xmlns:wfs=\"http://www.opengis.net/wfs/2.0\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0/wfs.xsd\">");
        if(boundingBox.isPresent()) {
            writer.write(boundingBox.get().toGmlString());
        }
        for(Tracker tracker : Tracker.TRACKERS) {
            writer.write("<gml:featureMember>");
            writer.write(tracker.toGmlString());
            writer.write("</gml:featureMember>");
        }
        for(Area area : Area.AREAS) {
            writer.write("<gml:featureMember>");
            writer.write(area.toGmlString());
            writer.write("</gml:featureMember>");
        }
        writer.write("</gml:FeatureCollection>");
    }
}
