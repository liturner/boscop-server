package de.turnertech.thw.cop.ows.model.area;

import java.util.List;
import java.util.UUID;

import de.turnertech.thw.cop.util.Coordinate;
import de.turnertech.thw.cop.util.DataObject;

public class Area implements DataObject {
    
    private String id;

    private List<Coordinate> geometry;

    private String areaType;

    public Area() {
        id = UUID.randomUUID().toString();
    }

    public List<Coordinate> getGeometry() {
        return geometry;
    }

    public void setGeometry(List<Coordinate> polygon) {
        this.geometry = polygon;
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

}
