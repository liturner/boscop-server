package de.turnertech.ows.servlet;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.common.ExceptionCode;
import de.turnertech.ows.common.Model;
import de.turnertech.ows.common.OwsContext;
import de.turnertech.ows.common.OwsRequestContext;
import de.turnertech.ows.common.RequestHandler;
import de.turnertech.ows.gml.BoundingBox;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.parameter.ResultType;
import de.turnertech.ows.parameter.WfsRequestParameter;
import de.turnertech.ows.srs.SpatialReferenceSystemRepresentation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsGetFeatureRequest implements RequestHandler {
    
    private static Optional<BoundingBox> getBoundingBox(HttpServletRequest request) {
        String bboxTypeString = WfsRequestParameter.findValue(request, WfsRequestParameter.BBOX).orElse(null);
        if(bboxTypeString == null || bboxTypeString.trim().equals("")) {
            return Optional.empty();
        }
        String[] parts = bboxTypeString.split(",", 5);
        return Optional.of(new BoundingBox(Double.valueOf(parts[1]), Double.valueOf(parts[0]), Double.valueOf(parts[3]), Double.valueOf(parts[2])));
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, OwsContext owsContext, OwsRequestContext requestContext) throws ServletException, IOException {
        final String resultTypeString = WfsRequestParameter.findValue(request, WfsRequestParameter.RESULTTYPE).orElse(ResultType.RESULTS.toString());
        final ResultType resultType = ResultType.valueOfIgnoreCase(resultTypeString);

        List<FeatureType> typenames = requestContext.getFeatureTypes();

        Optional<BoundingBox> requestedBoundingBox = getBoundingBox(request);

        BoundingBox actualBoundingBox = null;
    
        response.setContentType(RequestHandler.CONTENT_XML);

        Collection<IFeature> features = new LinkedList<>();
        for (FeatureType featureType : typenames) {
            Model model = owsContext.getModelProvider().getModel(featureType);
            if(requestedBoundingBox.isPresent()) {
                features.addAll(model.filter(requestedBoundingBox.get()));
            } else {
                features.addAll(model.getAll());
                if(actualBoundingBox == null) {
                    actualBoundingBox = model.getBoundingBox();
                }
                if(actualBoundingBox != null) {
                    actualBoundingBox.expandToFit(model.getBoundingBox());
                }
            }
        }

        response.setContentType(RequestHandler.CONTENT_XML);
        XMLStreamWriter out = null;
        try {
            out = XMLOutputFactory.newInstance().createXMLStreamWriter(response.getOutputStream(), "UTF-8");
            out.writeStartDocument("UTF-8", "1.0");
            if(ResultType.HITS == resultType) {
                out.writeEmptyElement("FeatureCollection");
            } else {
                out.writeStartElement("FeatureCollection");
                out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.GML_URI), OwsContext.GML_URI);
                List<String> writtenNamespaces = new ArrayList<>(features.size());
                for(FeatureType typename : typenames) {
                    if(!writtenNamespaces.contains(typename.getNamespace())) {
                        out.writeNamespace(owsContext.getXmlNamespacePrefix(typename.getNamespace()), typename.getNamespace());
                        writtenNamespaces.add(typename.getNamespace());
                    }
                }
            }
            out.writeDefaultNamespace(OwsContext.WFS_URI);

            final String wfsSchema = owsContext.getXmlNamespaceSchema(OwsContext.WFS_URI);
            if(wfsSchema != null) {
                out.writeNamespace(owsContext.getXmlNamespacePrefix(OwsContext.XSI_URI), OwsContext.XSI_URI);
                out.writeAttribute("xsi:schemaLocation", OwsContext.WFS_URI + " " + wfsSchema);
            }

            out.writeAttribute(OwsContext.WFS_URI, "timeStamp", OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).atZoneSameInstant(ZoneOffset.UTC).toString());
            out.writeAttribute(OwsContext.WFS_URI, "numberMatched", Integer.toString(features.size()));
            out.writeAttribute(OwsContext.WFS_URI, "numberReturned", ResultType.HITS == resultType ? "0" : Integer.toString(features.size()));

            if(ResultType.RESULTS == resultType) {

                if(features.size() > 0 && requestedBoundingBox.isPresent()) {
                    requestedBoundingBox.get().writeGml(out);
                } else if(features.size() > 0 && actualBoundingBox != null) {
                    actualBoundingBox.writeGml(out);
                }

                for (IFeature feature : features) {
                    out.writeStartElement(OwsContext.GML_URI, "featureMember");
                    feature.writeGml(out, feature.getFeatureType().getName(), feature.getFeatureType().getNamespace(), SpatialReferenceSystemRepresentation.EPSG4327_URI);
                    out.writeEndElement();
                }
            }

            if(ResultType.HITS != resultType) {
                out.writeEndElement();
            }

            out.writeEndDocument();
        } catch (Exception e) {
            response.sendError(500, ErrorServlet.encodeMessage(ExceptionCode.OPERATION_PROCESSING_FAILED.toString(), "GetFeatures", "XML Construction of the response failed."));
        }
    }
}
