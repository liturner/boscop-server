package de.turnertech.thw.cop.gml;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.util.Unit;
import de.turnertech.thw.cop.util.UnitConverter;

/**
 * gml:measure
 */
public class Distance implements GmlElement {

    public static final String GML_NAME = "measure";
    
    private final double value;

    private final Unit unit;

    public static final Double MIN_TOLERANCE = 1e-15;

    private Distance(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static Distance fromMetres(double metres) {
        return new Distance(metres, Unit.METRE);
    }

    public Distance inMetres() {
        Double newValue = UnitConverter.convert(unit, Unit.METRE, value).orElse(null);
        return newValue == null ? null : Distance.fromMetres(newValue);
    }

    public static Distance fromKilometres(double kilometres) {
        return new Distance(kilometres, Unit.KILOMETRE);
    }

    public Distance inKilometres() {
        Double newValue = UnitConverter.convert(unit, Unit.KILOMETRE, value).orElse(null);
        return newValue == null ? null : Distance.fromKilometres(newValue);
    }

    public static Distance fromFeet(double feet) {
        return new Distance(feet, Unit.FOOT);
    }

    public Distance inFeet() {
        Double newValue = UnitConverter.convert(unit, Unit.FOOT, value).orElse(null);
        return newValue == null ? null : Distance.fromFeet(newValue);
    }

    public static Distance fromNauticalMiles(double nauticalMiles) {
        return new Distance(nauticalMiles, Unit.NAUTICAL_MILE);
    }

    public Distance inNauticalMiles() {
        Double newValue = UnitConverter.convert(unit, Unit.NAUTICAL_MILE, value).orElse(null);
        return newValue == null ? null : Distance.fromNauticalMiles(newValue);
    }

    public double getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return Double.toString(value) + " " + unit.getSymbol();
    }

    public boolean equalsDistance(Distance other) {
        return equalsDistance(other, MIN_TOLERANCE);
    }

    public boolean equalsDistance(Distance other, double tolerance) {
        if (this == other)
            return true;
        if (other == null)
            return false;

        tolerance = tolerance < MIN_TOLERANCE ? MIN_TOLERANCE : tolerance;

        Double otherValueInOurUnit = UnitConverter.convert(other.unit, this.unit, other.value).orElse(null);
        return Math.abs(value - otherValueInOurUnit) < tolerance;
    }

    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            
            // OGC specifies that this is the symbol, not the URN, and to use symbols from UCUM
            out.writeAttribute("uom", unit.getSymbol());
            out.writeCharacters(Double.toString(value));
            
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for Circle");
        }
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

}
