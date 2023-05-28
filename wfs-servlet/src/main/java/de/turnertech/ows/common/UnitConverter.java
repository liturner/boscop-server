package de.turnertech.ows.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UnitConverter {
    
    private UnitConverter() {}

    private static final Map<Unit[], Double> scalarMap = new HashMap<>();

    static {
        scalarMap.put(new Unit[]{Unit.NAUTICAL_MILE, Unit.CENTIMETRE}, 185200.0);
        scalarMap.put(new Unit[]{Unit.NAUTICAL_MILE, Unit.METRE}, 1852.0);
        scalarMap.put(new Unit[]{Unit.NAUTICAL_MILE, Unit.KILOMETRE}, 1.852);
        scalarMap.put(new Unit[]{Unit.KILOMETRE, Unit.METRE}, 1000.0);
        scalarMap.put(new Unit[]{Unit.KILOMETRE, Unit.CENTIMETRE}, 100000.0);
        scalarMap.put(new Unit[]{Unit.METRE, Unit.CENTIMETRE}, 100.0);
        scalarMap.put(new Unit[]{Unit.INCH, Unit.CENTIMETRE}, 2.54);
        scalarMap.put(new Unit[]{Unit.FOOT, Unit.METRE}, 0.3048);
    }

    public static Double putScalar(Unit from, Unit to, double scalar) {
        if(scalar == 0.0) {
            throw new ArithmeticException("Do not add a scalar value of 0 to UnitConverter. This can cause divide by 0 fails in the event the scalar is used as a divisor");
        }
        return scalarMap.put(new Unit[]{from, to}, scalar);
    }


    public static Optional<Double> convert(Unit from, Unit to, double value) {
        if(from == to) {
            return Optional.of(value);
        }

        Double scalar = scalarMap.getOrDefault(new Unit[]{from, to}, null);
        if(scalar != null) {
            return Optional.of(value * scalar);
        }
        
        Double divisor = scalarMap.getOrDefault(new Unit[]{to, from}, null);
        if(divisor != null) {
            return Optional.of(value / divisor);
        }

        return Optional.empty();
    }
}
