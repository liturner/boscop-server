package de.turnertech.thw.cop.ows;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.model.area.AreaModel;
import de.turnertech.thw.cop.model.hazard.HazardModel;
import de.turnertech.thw.cop.model.unit.UnitModel;
import de.turnertech.thw.cop.ows.parameter.ResultType;
import de.turnertech.thw.cop.ows.parameter.WfsRequestParameter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsGetFeatureRequest {
    
    private WfsGetFeatureRequest() {
        
    }

    private static List<String> getTypenames(HttpServletRequest request) {
        String resultTypeString = WfsRequestParameter.findValue(request, WfsRequestParameter.TYPENAMES).orElse(null);
        if(resultTypeString == null || resultTypeString.trim().equals("")) {
            return Collections.emptyList();
        }
        return Arrays.asList(resultTypeString.split(","));
    }

    private static Optional<BoundingBox> getBoundingBox(HttpServletRequest request) {
        String bboxTypeString = WfsRequestParameter.findValue(request, WfsRequestParameter.BBOX).orElse(null);
        if(bboxTypeString == null || bboxTypeString.trim().equals("")) {
            return Optional.empty();
        }
        String[] parts = bboxTypeString.split(",", 5);
        return Optional.of(new BoundingBox(Double.valueOf(parts[1]), Double.valueOf(parts[0]), Double.valueOf(parts[3]), Double.valueOf(parts[2])));
    }

    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String resultTypeString = WfsRequestParameter.findValue(request, WfsRequestParameter.RESULTTYPE).orElse(ResultType.RESULTS.toString());
        final ResultType resultType = ResultType.valueOfIgnoreCase(resultTypeString);

        // No safety check, as we check in the WfsFilter class
        List<String> typenames = getTypenames(request);

        Optional<BoundingBox> boundingBox = getBoundingBox(request);

        response.setContentType(Constants.ContentTypes.XML);
        if(ResultType.HITS == resultType) {
            doGetHits(request, response, typenames, boundingBox);
        } else if (ResultType.RESULTS == resultType) {
            doGetResults(request, response, typenames, boundingBox);
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
    private static void doGetHits(HttpServletRequest request, HttpServletResponse response, List<String> typenames, Optional<BoundingBox> boundingBoxLimit) throws ServletException, IOException {
        int count = 0;

        List<Feature> areas = null;
        if(typenames.contains(AreaModel.TYPENAME)) {
            if(boundingBoxLimit.isPresent()) {
                areas = Collections.unmodifiableList(AreaModel.INSTANCE.filter(boundingBoxLimit.get()));
            } else {
                areas = Collections.unmodifiableList(AreaModel.INSTANCE.getAll());
            }
        } else {
            areas = Collections.emptyList();
        }
        List<Feature> units = null;
        if(typenames.contains(UnitModel.TYPENAME)) {
            if(boundingBoxLimit.isPresent()) {
                units = Collections.unmodifiableList(UnitModel.INSTANCE.filter(boundingBoxLimit.get()));
            } else {
                units = Collections.unmodifiableList(UnitModel.INSTANCE.getAll());
            }
        } else {
            units = Collections.emptyList();
        }
        List<Feature> hazards = null;
        if(typenames.contains(HazardModel.TYPENAME)) {
            if(boundingBoxLimit.isPresent()) {
                hazards = Collections.unmodifiableList(HazardModel.INSTANCE.filter(boundingBoxLimit.get()));
            } else {
                hazards = Collections.unmodifiableList(HazardModel.INSTANCE.getAll());
            }
        } else {
            hazards = Collections.emptyList();
        }

        count += areas.size();
        count += units.size();
        count += hazards.size();
        
        PrintWriter writer = response.getWriter();
        writer.write("<?xml version=\"1.0\"?>\n");
        writer.write("<wfs:FeatureCollection timeStamp=\"");
        writer.write(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        writer.write("\" numberMatched=\"");
        writer.write(count);
        writer.write("\" numberReturned=\"0\" xmlns:wfs=\"http://www.opengis.net/wfs/2.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0/wfs.xsd\"/>");        
    }

    private static void doGetResults(HttpServletRequest request, HttpServletResponse response, List<String> typenames, Optional<BoundingBox> boundingBox) throws ServletException, IOException {
        
        int count = 0;

        List<Feature> areas = null;
        if(typenames.contains(AreaModel.TYPENAME)) {
            if(boundingBox.isPresent()) {
                areas = Collections.unmodifiableList(AreaModel.INSTANCE.filter(boundingBox.get()));
            } else {
                areas = Collections.unmodifiableList(AreaModel.INSTANCE.getAll());
            }
        } else {
            areas = Collections.emptyList();
        }
        List<Feature> units = null;
        if(typenames.contains(UnitModel.TYPENAME)) {
            if(boundingBox.isPresent()) {
                units = Collections.unmodifiableList(UnitModel.INSTANCE.filter(boundingBox.get()));
            } else {
                units = Collections.unmodifiableList(UnitModel.INSTANCE.getAll());
            }
        } else {
            units = Collections.emptyList();
        }
        List<Feature> hazards = null;
        if(typenames.contains(HazardModel.TYPENAME)) {
            if(boundingBox.isPresent()) {
                hazards = Collections.unmodifiableList(HazardModel.INSTANCE.filter(boundingBox.get()));
            } else {
                hazards = Collections.unmodifiableList(HazardModel.INSTANCE.getAll());
            }
        } else {
            hazards = Collections.emptyList();
        }

        count += areas.size();
        count += units.size();
        count += hazards.size();

        PrintWriter writer = response.getWriter();
        writer.write("<?xml version=\"1.0\"?>\n");
        writer.write("<gml:FeatureCollection timeStamp=\"");
        writer.write(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        writer.write("\" numberMatched=\"");
        writer.write(String.valueOf(count));
        writer.write("\" numberReturned=\"");
        writer.write(String.valueOf(count));
        writer.write("\" xmlns:boscop=\"urn:ns:de:turnertech:boscop\" xmlns:wfs=\"http://www.opengis.net/wfs/2.0\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/wfs/2.0 http://schemas.opengis.net/wfs/2.0/wfs.xsd\">");
        if(count > 0 && boundingBox.isPresent()) {
            writer.write(boundingBox.get().toGmlString());
        }
        for(Feature tracker : units) {
            writer.write("<gml:featureMember>");
            writer.write(tracker.toGmlString());
            writer.write("</gml:featureMember>");
        }
        for(Feature area : areas) {
            writer.write("<gml:featureMember>");
            writer.write(area.toGmlString());
            writer.write("</gml:featureMember>");
        }
        for(Feature hazard : hazards) {
            writer.write("<gml:featureMember>");
            writer.write(hazard.toGmlString());
            writer.write("</gml:featureMember>");
        }
        writer.write("</gml:FeatureCollection>");
    }
}
