package de.turnertech.ows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.gml.DirectPosition;
import de.turnertech.ows.srs.SpatialReferenceSystem;
import de.turnertech.ows.srs.SpatialReferenceSystemConverter;

public class SrsTests {
    
    @Test
    void EPSG3857() {
        assertEquals(0, SpatialReferenceSystem.EPSG3857.getXIndex());
        assertEquals(1, SpatialReferenceSystem.EPSG4326.getXIndex());
    }

    @Test
    void EPSG4326toEPSG3857() {
        DirectPosition pos = new DirectPosition(SpatialReferenceSystem.EPSG4326, 0.0, 0.0);
        Optional<DirectPosition> convertedPosition = SpatialReferenceSystemConverter.convertDirectPosition(pos, SpatialReferenceSystem.EPSG3857);
        assertNotNull(convertedPosition);
        assertTrue(convertedPosition.isPresent());
        assertEquals(0.0, convertedPosition.get().getX());
        assertEquals(0.0, convertedPosition.get().getY());

        pos = new DirectPosition(SpatialReferenceSystem.EPSG4326, 10.0, 10.0);
        convertedPosition = SpatialReferenceSystemConverter.convertDirectPosition(pos, SpatialReferenceSystem.EPSG3857);
        assertNotNull(convertedPosition);
        assertTrue(convertedPosition.isPresent());
        assertTrue(convertedPosition.get().getX() > 1113194.90);
        assertTrue(convertedPosition.get().getX() < 1113194.91);
        assertTrue(convertedPosition.get().getY() > 1118889.97);
        assertTrue(convertedPosition.get().getY() < 1118889.98);

        pos = new DirectPosition(SpatialReferenceSystem.EPSG4326, -80.0, -80.0);
        convertedPosition = SpatialReferenceSystemConverter.convertDirectPosition(pos, SpatialReferenceSystem.EPSG3857);
        assertNotNull(convertedPosition);
        assertTrue(convertedPosition.isPresent());
        assertTrue(convertedPosition.get().getX() < -8905559.26);
        assertTrue(convertedPosition.get().getX() > -8905559.27);
        assertTrue(convertedPosition.get().getY() < -15538711.09);
        assertTrue(convertedPosition.get().getY() > -15538711.10);
    }

    @Test
    void EPSG3857toEPSG4326() {
        DirectPosition pos = new DirectPosition(SpatialReferenceSystem.EPSG3857, 0.0, 0.0);
        Optional<DirectPosition> convertedPosition = SpatialReferenceSystemConverter.convertDirectPosition(pos, SpatialReferenceSystem.EPSG4326);
        assertNotNull(convertedPosition);
        assertTrue(convertedPosition.isPresent());
        assertEquals(0.0, convertedPosition.get().getX());
        assertEquals(0.0, convertedPosition.get().getY());

        pos = new DirectPosition(SpatialReferenceSystem.EPSG3857, 1113194.9079, 1118889.9748);
        convertedPosition = SpatialReferenceSystemConverter.convertDirectPosition(pos, SpatialReferenceSystem.EPSG4326);
        assertNotNull(convertedPosition);
        assertTrue(convertedPosition.isPresent());
        assertTrue(convertedPosition.get().getX() > 9.999);
        assertTrue(convertedPosition.get().getX() < 10.001);
        assertTrue(convertedPosition.get().getY() > 9.999);
        assertTrue(convertedPosition.get().getY() < 10.001);

        pos = new DirectPosition(SpatialReferenceSystem.EPSG3857, -8905559.2634, -15538711.0963);
        convertedPosition = SpatialReferenceSystemConverter.convertDirectPosition(pos, SpatialReferenceSystem.EPSG4326);
        assertNotNull(convertedPosition);
        assertTrue(convertedPosition.isPresent());
        assertTrue(convertedPosition.get().getX() < -79.999);
        assertTrue(convertedPosition.get().getX() > -80.001);
        assertTrue(convertedPosition.get().getY() < -79.999);
        assertTrue(convertedPosition.get().getY() > -80.001);
    }

}
