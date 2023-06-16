package de.turnertech.ows;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.gml.SpatialReferenceSystem;

public class SrsTests {
    
    @Test
    void EPSG3857() {
        assertEquals(0, SpatialReferenceSystem.EPSG3857.getXIndex());
        assertEquals(1, SpatialReferenceSystem.EPSG4326.getXIndex());
    }

}
