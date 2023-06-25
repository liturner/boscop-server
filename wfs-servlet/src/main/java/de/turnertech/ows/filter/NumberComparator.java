package de.turnertech.ows.filter;

import java.util.Comparator;

/*
 * TODO: This can be made more accurate.
 */
public class NumberComparator implements Comparator<Number> {

    @Override
    public int compare(Number leftNumber, Number rightNumber) {
        if(leftNumber instanceof Double || rightNumber instanceof Double) {
            return Double.compare(leftNumber.doubleValue(), rightNumber.doubleValue());
        } else if (leftNumber instanceof Float || rightNumber instanceof Float) {
            return Float.compare(leftNumber.floatValue(), rightNumber.floatValue());
        } else {
            // Long has enough memory to handle all other whole number types.
            return Long.compare(leftNumber.longValue(), rightNumber.longValue());
        }
    }
    
}
