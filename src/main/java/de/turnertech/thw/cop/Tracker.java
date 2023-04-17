package de.turnertech.thw.cop;

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
        if(tracker.opta == null) {
            return Optional.of("Empty OPTA provided is not valid.");
        } else if (tracker.opta.length() > 24) {
            return Optional.of("Provided OPTA is longer than 24 charachters, and is not valid.");
        }
        
        return Optional.empty();
    }

}
