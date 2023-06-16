package de.turnertech.ows;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.gml.DirectPosition;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class DirectPositionTests {
    
    @Test
    void SrsDimensionOrder() {
        DirectPosition posA = new DirectPosition(SpatialReferenceSystem.EPSG4326, 10.0, 20.0);
        DirectPosition posB = new DirectPosition(SpatialReferenceSystem.EPSG3857, 10.0, 20.0);

        assertEquals(10.0, posA.getX());
        assertEquals(20.0, posA.getY());
        assertEquals("20.0 10.0", posA.toString());

        assertEquals(10.0, posB.getX());
        assertEquals(20.0, posB.getY());
        assertEquals("10.0 20.0", posB.toString());
    }

}
