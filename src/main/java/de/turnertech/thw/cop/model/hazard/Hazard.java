package de.turnertech.thw.cop.model.hazard;

import java.util.UUID;

import de.turnertech.thw.cop.gml.BoundingBox;
import de.turnertech.thw.cop.gml.Feature;

public class Hazard implements Feature {
    
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
        this.latitude = latitude;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.from(this);
    }

    @Override
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