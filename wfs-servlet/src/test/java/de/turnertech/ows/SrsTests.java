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

        DirectPosition posB = convertedPosition.get();

        pos = new DirectPosition(SpatialReferenceSystem.EPSG4326, 10.0, 10.0);
        convertedPosition = SpatialReferenceSystemConverter.convertDirectPosition(pos, SpatialReferenceSystem.EPSG3857);
        assertNotNull(convertedPosition);
        assertTrue(convertedPosition.isPresent());

        posB = convertedPosition.get();
    }

}
