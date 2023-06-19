package de.turnertech.ows.srs;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

import de.turnertech.ows.Logging;
import de.turnertech.ows.gml.DirectPosition;

public class SpatialReferenceSystemConverter {
    
    private static final MathContext MATH_CONTEXT = new MathContext(13);

    private SpatialReferenceSystemConverter() {
        // Static Converter
    }

    public static Optional<DirectPosition> convertDirectPosition(DirectPosition pos, SpatialReferenceSystem targetSrs) {
        if(pos.getSrs().equals(targetSrs)) {
            return Optional.of(pos);
        }

        DirectPosition epsg4326Pos = null;
        switch(pos.getSrs()) {
            case CRS84:
                epsg4326Pos = convertCRS84toEPSG4326(pos);
                break;
            case EPSG3857:
                epsg4326Pos = convertEPSG3857toEPSG4326(pos);
                break;
            case EPSG4326:
                epsg4326Pos = pos;
                break;
            default:
                Logging.LOG.severe("Could not convert from unsupported SRS: "+ pos.getSrs().toString());
                return Optional.empty();
        }

        switch(targetSrs) {
            case CRS84:
                return Optional.of(convertEPSG4326toCRS84(epsg4326Pos));
            case EPSG3857:
                return Optional.of(convertEPSG4326toEPSG3857(epsg4326Pos));
            case EPSG4326:
                return Optional.of(epsg4326Pos);
            default:
                Logging.LOG.severe("Could not convert from unsupported SRS: "+ pos.getSrs().toString());
                return Optional.empty();
        }
    }

    private static DirectPosition convertEPSG4326toCRS84(DirectPosition pos) {
        return new DirectPosition(SpatialReferenceSystem.CRS84, pos.getX(), pos.getY());
    }

    private static DirectPosition convertEPSG4326toEPSG3857(DirectPosition pos) {
        double x = pos.getX();
        double y = pos.getY();
        x = (x * 20037508.34) / 180.0;
        y = Math.log(Math.tan(((90.0 + y) * Math.PI) / 360.0)) / (Math.PI / 180.0);
        y = (y * 20037508.34) / 180.0;
        return new DirectPosition(SpatialReferenceSystem.EPSG3857, BigDecimal.valueOf(x).setScale(13, RoundingMode.HALF_UP).doubleValue(), BigDecimal.valueOf(y).setScale(8, RoundingMode.HALF_UP).doubleValue());
    }

    private static DirectPosition convertCRS84toEPSG4326(DirectPosition pos) {
        return new DirectPosition(SpatialReferenceSystem.EPSG4326, pos.getX(), pos.getY());
    }

    private static DirectPosition convertEPSG3857toEPSG4326(DirectPosition pos) {
        double x = pos.getX();
        double y = pos.getY();
        x = (x * 180.0) / 20037508.34;
        y = (y * 180.0) / 20037508.34;
        y = (Math.atan(Math.pow(Math.E, y * (Math.PI / 180.0))) * 360.0) / Math.PI - 90.0;
        return new DirectPosition(SpatialReferenceSystem.EPSG4326, BigDecimal.valueOf(x).round(MATH_CONTEXT).doubleValue(), BigDecimal.valueOf(y).round(MATH_CONTEXT).doubleValue());
    }

}
