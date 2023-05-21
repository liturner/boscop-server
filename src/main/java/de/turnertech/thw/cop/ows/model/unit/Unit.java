package de.turnertech.thw.cop.ows.model.unit;

import java.util.Optional;

import de.turnertech.thw.cop.util.BoundingBox;
import de.turnertech.thw.cop.util.DataObject;
import de.turnertech.thw.cop.util.OPTA;
import de.turnertech.thw.cop.util.PositionProvider;

public class Unit implements PositionProvider, DataObject {
    
    /**
     * See OPTA in THW-FuRnR.
     * 
     * We use the free space to assign additional units. E.g. seperate members of a bergungsgruppe.
     */
    private String opta;

    private Double latitude;

    private Double longitude;

    public Unit() {
        opta = "";
        latitude = 0.0;
        longitude = 0.0;
    }

    @Override
    public String getId() {
        return opta;
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

    public static Optional<String> validate(Unit tracker) {
        if(!OPTA.isValid(tracker.opta)) {
            // Todo: Make this a more exmplanatory error.
            return Optional.of("OPTA is Invalid");
        }
        
        return Optional.empty();
    }

    @Override
    public String toGmlString() {
        final String gmlId = getOpta().replace(' ', '_');

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<boscop:Unit gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("\"><boscop:geometry><gml:Point srsName=\"http://www.opengis.net/def/crs/EPSG/0/4326\" gml:id=\"");
        stringBuilder.append(gmlId);
        stringBuilder.append("-geometry\"><gml:pos>");
        stringBuilder.append(String.valueOf(getLatitude()));
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(getLongitude()));
        stringBuilder.append("</gml:pos></gml:Point></boscop:geometry><boscop:opta>");
        stringBuilder.append(getOpta());
        stringBuilder.append("</boscop:opta></boscop:Unit>");
        return stringBuilder.toString();
    }

}
