package de.turnertech.thw.cop.model.hazard;

import java.util.UUID;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.gml.IFeature;
import de.turnertech.thw.cop.gml.Point;
import de.turnertech.thw.cop.gml.SpatialReferenceSystem;
import de.turnertech.thw.cop.gml.SpatialReferenceSystemRepresentation;
import de.turnertech.thw.cop.ows.api.OwsContext;
import de.turnertech.thw.cop.util.PositionProvider;

public class Hazard implements IFeature, PositionProvider {
    
    public final String GML_NAME = "Feature";

    public static final FeatureType FEATURE_TYPE;

    private String id;

    private String hazardType;

    private Point geometry;

    static {
        FEATURE_TYPE = new FeatureType(Constants.Model.NAMESPACE, "Hazard");
        FEATURE_TYPE.setSrs(SpatialReferenceSystem.EPSG4327);
    }

    public Hazard() {
        id = UUID.randomUUID().toString();
        hazardType = "";
        geometry = new Point();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setHazardType(String hazardType) {
        this.hazardType = hazardType;
    }

    public String getHazardType() {
        return this.hazardType;
    }

    public void setLatitude(Double latitude) {
        this.geometry.setY(latitude);
    }

    @Override
    public double getLatitude() {
        return this.geometry.getY();
    }

    public void setLongitude(Double longitude) {
        this.geometry.setX(longitude);
    }

    @Override
    public double getLongitude() {
        return this.geometry.getX();
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.from(this);
    }

    public String toGmlString() {
        String gmlId = id;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<boscop:Hazard gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("\"><boscop:geometry><gml:Point srsName=\"http://www.opengis.net/def/crs/EPSG/0/4326\" gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("-geometry\"><gml:pos>");
        stringBuilder.append(String.valueOf(getLatitude()));
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(getLongitude()));
        stringBuilder.append("</gml:pos></gml:Point></boscop:geometry><boscop:hazardType>");
        stringBuilder.append(hazardType);
        stringBuilder.append("</boscop:hazardType></boscop:Hazard>");
        return stringBuilder.toString();
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeAttribute(OwsContext.GML_URI, "id", getId());

            out.writeStartElement(FEATURE_TYPE.getNamespace(), "hazardType");
            out.writeCharacters(hazardType);
            out.writeEndElement();

            out.writeStartElement(FEATURE_TYPE.getNamespace(), "geometry");
            geometry.writeGml(out);
            out.writeEndElement();

            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for Feature");
        }   
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

    @Override
    public FeatureType getFeatureType() {
        return FEATURE_TYPE;
    }
}