package de.turnertech.thw.cop.wfs;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.trackers.Tracker;
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
        writer.write("1");
        writer.write("\" numberReturned=\"0\" xmlns:wfs=\"http://www.opengis.net/wfs/2.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0/wfs.xsd\"/>");        
    }

    private static void doGetResults(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(Constants.ContentTypes.XML);
        PrintWriter writer = response.getWriter();
        writer.write("<?xml version=\"1.0\"?>\n");
        writer.write("<wfs:FeatureCollection timeStamp=\"");
        writer.write(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        writer.write("\" numberMatched=\"");
        writer.write("1");
        writer.write("\" numberReturned=\"1\" xmlns:boscop=\"urn:ns:de:turnertech:boscop\" xmlns:wfs=\"http://www.opengis.net/wfs/2.0\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0/wfs.xsd\">");
        writer.write(
            """
                <wfs:boundedBy>
                    <gml:Envelope srsName="urn:ogc:def:crs:EPSG::4326">
                        <gml:lowerCorner>-180 -90</gml:lowerCorner>
                        <gml:upperCorner>180 90</gml:upperCorner>
                    </gml:Envelope>
                </wfs:boundedBy>
            """
        );
        for(Tracker tracker : Tracker.TRACKERS) {
            writer.write("<wfs:member>");
            writer.write(tracker.toGmlString());
            writer.write("</wfs:member>");
        }
        writer.write("</wfs:FeatureCollection>");
    }
}
