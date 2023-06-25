package de.turnertech.ows.filter;

import java.util.Objects;
import java.util.function.BiPredicate;

public enum BinaryComparisonName implements BiPredicate<Object, Object>  {
    PROPERTY_IS_EQUAL_TO("PropertyIsEqualTo"),
    PROPERTY_IS_NOT_EQUAL_TO("PropertyIsNotEqualTo"),
    PROPERTY_IS_LESS_THAN("PropertyIsLessThan"),
    PROPERTY_IS_GREATER_THAN("PropertyIsGreaterThan"),
    PROPERTY_IS_LESS_THAN_OR_EQUAL_TO("PropertyIsLessThanOrEqualTo"),
    PROPERTY_IS_GREATER_THAN_OR_EQUAL_TO("PropertyIsGreaterThanOrEqualTo");

    private static final NumberComparator NUMBER_COMPARATOR = new NumberComparator();

    private final String xmlName;

    private BinaryComparisonName(final String xmlName) {
        this.xmlName = xmlName;
    }

    @Override
    public boolean test(Object left, Object right) {
        return this.test(left, right, true);
    }

    public boolean test(Object left, Object right, boolean matchCase) {

        // Special case handling for null.
        if(left == null && right == null && this.equals(PROPERTY_IS_EQUAL_TO)) {
            return true;
        }
        if(left == null || right == null) {
            return false;
        }

        if(this.equals(PROPERTY_IS_EQUAL_TO)) {
            return Objects.equals(left, right);
        } else if(this.equals(PROPERTY_IS_NOT_EQUAL_TO)) {
            return !Objects.equals(left, right);
        }

        if(left instanceof Number && right instanceof Number) {
            return test((Number)left, (Number)right);
        }
        if(left instanceof String && right instanceof String) {
            return test((String)left, (String)right, matchCase);
        }

        return false;
    }

    private boolean test(Number leftNumber, Number rightNumber) {
        switch(this) {
            case PROPERTY_IS_GREATER_THAN:
                return NUMBER_COMPARATOR.compare(leftNumber, rightNumber) > 0.0;
            case PROPERTY_IS_GREATER_THAN_OR_EQUAL_TO:
                return NUMBER_COMPARATOR.compare(leftNumber, rightNumber) >= 0.0;
            case PROPERTY_IS_LESS_THAN:
                return NUMBER_COMPARATOR.compare(leftNumber, rightNumber) < 0.0;
            case PROPERTY_IS_LESS_THAN_OR_EQUAL_TO:
                return NUMBER_COMPARATOR.compare(leftNumber, rightNumber) <= 0.0;
            default:
                return false;        
        }
    }

    private boolean test(String leftString, String rightString, boolean matchCase) {
        switch(this) {
            case PROPERTY_IS_GREATER_THAN:
                return (matchCase ? leftString.compareTo(rightString) : leftString.compareToIgnoreCase(rightString)) > 0.0;
            case PROPERTY_IS_GREATER_THAN_OR_EQUAL_TO:
                return (matchCase ? leftString.compareTo(rightString) : leftString.compareToIgnoreCase(rightString)) >= 0.0;
            case PROPERTY_IS_LESS_THAN:
                return (matchCase ? leftString.compareTo(rightString) : leftString.compareToIgnoreCase(rightString)) < 0.0;
            case PROPERTY_IS_LESS_THAN_OR_EQUAL_TO:
                return (matchCase ? leftString.compareTo(rightString) : leftString.compareToIgnoreCase(rightString)) <= 0.0;
            default:
                return false;        
        }
    }

    @Override
    public String toString() {
        return xmlName;
    }

    public static BinaryComparisonName fromString(final String comparator) {
        for(BinaryComparisonName entry : BinaryComparisonName.values()) {
            if(entry.toString().equals(comparator)) {
                return entry;
            }
        }
        return null;
    }
}
