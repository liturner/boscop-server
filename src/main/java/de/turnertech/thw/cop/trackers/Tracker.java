package de.turnertech.thw.cop.trackers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Tracker {
    
    public static final List<Tracker> TRACKERS = new ArrayList<>();

    /**
     * See OPTA in THW-FuRnR.
     * 
     * We use the free space to assign additional units. E.g. seperate members of a bergungsgruppe.
     */
    private String opta;

    private Double latitude;

    private Double longitude;

    public Tracker() {
        opta = "";
        latitude = 0.0;
        longitude = 0.0;
    }

    public void setOpta(String opta) {
        this.opta = opta;
    }

    public String getOpta() {
        return this.opta;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public static Optional<String> validate(Tracker tracker) {
        if(!OPTA.isValid(tracker.opta)) {
            // Todo: Make this a more exmplanatory error.
            return Optional.of("OPTA is Invalid");
        }
        
        return Optional.empty();
    }

    public String toGmlString() {
        final String gmlId = getOpta().replace(' ', '_');

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<boscop:Unit gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("\"><geometry><Point srsName=\"urn:ogc:def:crs:EPSG::4326\" gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("-geometry\"><pos>");
        stringBuilder.append(String.valueOf(getLatitude()));
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(getLongitude()));
        stringBuilder.append("</pos></Point></geometry><opta>");
        stringBuilder.append(getOpta());
        stringBuilder.append("</opta></boscop:Unit>");
        return stringBuilder.toString();
    }

}
