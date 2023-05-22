package de.turnertech.thw.cop.model.area;

import java.util.List;
import java.util.UUID;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Constants;
import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.Feature;
import de.turnertech.thw.cop.gml.FeatureType;
import de.turnertech.thw.cop.gml.SpatialReferenceSystem;
import de.turnertech.thw.cop.util.Coordinate;

public class Area implements Feature {
    
    public final String GML_NAME = "Feature";

    public static final FeatureType FEATURE_TYPE;

    private String id;

    private List<Coordinate> geometry;

    private String areaType;

    static {
        FEATURE_TYPE = new FeatureType(Constants.Model.NAMESPACE, "Area");
        FEATURE_TYPE.setSrs(SpatialReferenceSystem.EPSG4327);
    }

    public Area() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public List<Coordinate> getGeometry() {
        return geometry;
    }

    public void setGeometry(List<Coordinate> polygon) {
        this.geometry = polygon;
    }

    @Override
    public double getLatitude() {
        return getBoundingBox().centerPoint().getLatitude();
    }

    @Override
    public double getLongitude() {
        return getBoundingBox().centerPoint().getLongitude();
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.from(getGeometry());
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String toGmlString() {
        String gmlId = id;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<boscop:Area gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("\"><boscop:geometry><gml:Polygon srsName=\"http://www.opengis.net/def/crs/EPSG/0/4326\" gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("-geometry\"><gml:exterior><gml:LinearRing srsName=\"http://www.opengis.net/def/crs/EPSG/0/4326\"><gml:posList srsDimension=\"2\">");
        for(Coordinate coord : geometry) {
            stringBuilder.append(String.valueOf(coord.getLatitude()));
            stringBuilder.append(" ");
            stringBuilder.append(String.valueOf(coord.getLongitude()));
            stringBuilder.append(" ");
        }
        stringBuilder.append("</gml:posList></gml:LinearRing></gml:exterior></gml:Polygon></boscop:geometry>");
        stringBuilder.append("<boscop:areaType>");
        stringBuilder.append(areaType);
        stringBuilder.append("</boscop:areaType>");
        stringBuilder.append("</boscop:Area>");
        return stringBuilder.toString();
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            


            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for DirectPosition");
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
