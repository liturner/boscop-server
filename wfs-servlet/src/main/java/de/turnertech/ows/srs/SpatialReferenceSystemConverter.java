package de.turnertech.ows.srs;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

import de.turnertech.ows.gml.DirectPosition;

public class SpatialReferenceSystemConverter {
    
    private static final MathContext MATH_CONTEXT = new MathContext(5);

    private SpatialReferenceSystemConverter() {
        // Static Converter
    }

    public static Optional<DirectPosition> convertDirectPosition(DirectPosition pos, SpatialReferenceSystem targetSrs) {
        if(pos.getSrs().equals(targetSrs)) {
            return Optional.of(pos);
        }

        if(SpatialReferenceSystem.EPSG3857.equals(pos.getSrs()) && SpatialReferenceSystem.EPSG4326.equals(targetSrs)) {
            return Optional.of(convertEPSG3857toEPSG4326(pos));
        } else if(SpatialReferenceSystem.EPSG4326.equals(pos.getSrs()) && SpatialReferenceSystem.EPSG3857.equals(targetSrs)) {
            return Optional.of(convertEPSG4326toEPSG3857(pos));
        }

        return Optional.empty();
    }

    private static DirectPosition convertEPSG4326toEPSG3857(DirectPosition pos) {
        double x = pos.getX();
        double y = pos.getY();
        x = (x * 20037508.34) / 180;
        y = Math.log(Math.tan(((90 + y) * Math.PI) / 360)) / (Math.PI / 180);
        y = (y * 20037508.34) / 180;
        return new DirectPosition(SpatialReferenceSystem.EPSG3857, BigDecimal.valueOf(x).setScale(8, RoundingMode.HALF_UP).doubleValue(), BigDecimal.valueOf(y).setScale(8, RoundingMode.HALF_UP).doubleValue());
    }

    private static DirectPosition convertEPSG3857toEPSG4326(DirectPosition pos) {
        double x = pos.getX();
        double y = pos.getY();
        x = (x * 180) / 20037508.34;
        y = (y * 180) / 20037508.34;
        y = (Math.atan(Math.pow(Math.E, y * (Math.PI / 180))) * 360) / Math.PI - 90;
        return new DirectPosition(SpatialReferenceSystem.EPSG4326, BigDecimal.valueOf(x).round(MATH_CONTEXT).doubleValue(), BigDecimal.valueOf(y).round(MATH_CONTEXT).doubleValue());
    }

}
