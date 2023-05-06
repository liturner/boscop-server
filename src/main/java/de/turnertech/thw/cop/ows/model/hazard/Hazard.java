package de.turnertech.thw.cop.ows.model.hazard;

import java.util.UUID;

import de.turnertech.thw.cop.util.DataObject;
import de.turnertech.thw.cop.util.PositionProvider;

public class Hazard implements PositionProvider, DataObject {
    
    private String id;

    private String hazardType;

    private Double latitude;

    private Double longitude;

    public Hazard() {
        id = UUID.randomUUID().toString();
        hazardType = "";
        latitude = 0.0;
        longitude = 0.0;
    }

    public void setHazardType(String hazardType) {
        this.hazardType = hazardType;
    }

    public String getHazardType() {
        return this.hazardType;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
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
}