package de.turnertech.ows.common;

/**
 * @see <a href="https://unitsofmeasure.org/ucum">The Unified Code for Units of Measure</a>
 */
public enum Unit {

    // Length
    NAUTICAL_MILE("urn:ogc:def:uom:UCUM::nmi_i", "nmi"),
    CENTIMETRE("urn:ogc:def:uom:UCUM::cm", "cm"),
    METRE("urn:ogc:def:uom:UCUM::m", "m"),
    KILOMETRE("urn:ogc:def:uom:UCUM::km", "km"),
    INCH("urn:ogc:def:uom:UCUM::in_i", "in"),
    FOOT("urn:ogc:def:uom:UCUM::ft_i", "ft"),
    MILE("urn:ogc:def:uom:UCUM::mi_i", "mi");

    private final String urn;

    private final String symbol;

    private Unit(String urn, String symbol) {
        this.urn = urn;
        this.symbol = symbol;
    }

    public String getUrn() {
        return urn;
    }

    public String getSymbol() {
        return symbol;
    }

}
