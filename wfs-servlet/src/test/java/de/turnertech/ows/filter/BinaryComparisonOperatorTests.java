package de.turnertech.ows.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BinaryComparisonOperatorTests {
    
    @Test
    void lessThanTest() {
        assertTrue(new BinaryComparisonOperator(new Literal(5.9999), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(6)).getAsBoolean());
        assertTrue(new BinaryComparisonOperator(new Literal(0), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(0.1)).getAsBoolean());
        assertTrue(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(6)).getAsBoolean());
        assertTrue(new BinaryComparisonOperator(new Literal((byte)5), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(6l)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(4)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(4)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_LESS_THAN, null).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_LESS_THAN, null).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(null)).getAsBoolean());
        assertTrue(new BinaryComparisonOperator(new Literal("a"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("b")).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal("b"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("a")).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal("a"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("A")).getAsBoolean());
        assertTrue(new BinaryComparisonOperator(new Literal("A"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("a")).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal("A"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("a")).setMatchCase(false).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("a")).getAsBoolean());
    }

    @Test
    void greaterThanTest() {
        assertFalse(new BinaryComparisonOperator(new Literal(5.9999), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(6)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(0), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(0.1)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(6)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal((byte)5), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(6l)).getAsBoolean());
        assertTrue(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(4)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(4)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_GREATER_THAN, null).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, null).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(null)).getAsBoolean());
    }

    @Test
    void equalTest() {
        assertFalse(new BinaryComparisonOperator(new Literal(5.9999), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(6)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(0), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(0.1)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(6)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal((byte)5), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(6l)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(4)).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(4)).getAsBoolean());
        assertTrue(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_EQUAL_TO, null).getAsBoolean());
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, null).getAsBoolean());
        assertTrue(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(null)).getAsBoolean());
    }

}
