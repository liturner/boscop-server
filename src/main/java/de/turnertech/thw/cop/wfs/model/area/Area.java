package de.turnertech.thw.cop.wfs.model.area;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.turnertech.thw.cop.util.Coordinate;

public class Area {
    
    public static final List<Area> AREAS = new ArrayList<>();

    private String id;

    private List<Coordinate> polygon;

    public Area() {
        id = UUID.randomUUID().toString();
    }

    public List<Coordinate> getPolygon() {
        return polygon;
    }

    public void setPolygon(List<Coordinate> polygon) {
        this.polygon = polygon;
    }

    public String toGmlString() {
        String gmlId = id;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<boscop:Area gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("\"><boscop:geometry><gml:Polygon srsName=\"EPSG:4326\" gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("-geometry\"><gml:exterior><gml:LinearRing srsName=\"EPSG:4326\"><gml:posList srsDimension=\"2\">");
        for(Coordinate coord : polygon) {
            stringBuilder.append(String.valueOf(coord.getLatitude()));
            stringBuilder.append(" ");
            stringBuilder.append(String.valueOf(coord.getLongitude()));
            stringBuilder.append(" ");
        }
        stringBuilder.append("</gml:posList></gml:LinearRing></gml:exterior></gml:Polygon></boscop:geometry>");
        stringBuilder.append("</boscop:Area>");
        return stringBuilder.toString();
    }

}
