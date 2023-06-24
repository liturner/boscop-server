package de.turnertech.ows.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BasicTests {
    
    @Test
    void someTest() {
        assertTrue(new BinaryComparisonOperator(
            new Literal(5),
            BinaryComparisonName.PROPERTY_IS_LESS_THAN,
            new Literal(6)).getAsBoolean());

        assertFalse(new BinaryComparisonOperator(
            new Literal(5),
            BinaryComparisonName.PROPERTY_IS_LESS_THAN,
            new Literal(4)).getAsBoolean());
    }

}
