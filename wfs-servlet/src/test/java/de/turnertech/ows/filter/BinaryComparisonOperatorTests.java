package de.turnertech.ows.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.gml.Feature;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.FeaturePropertyType;
import de.turnertech.ows.gml.FeatureType;

public class BinaryComparisonOperatorTests {
    
    @Test
    void lessThanTest() {
        assertTrue(new BinaryComparisonOperator(new Literal(5.9999), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(6)).test(null));
        assertTrue(new BinaryComparisonOperator(new Literal(0), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(0.1)).test(null));
        assertTrue(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(6)).test(null));
        assertTrue(new BinaryComparisonOperator(new Literal((byte)5), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(6l)).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(4)).test(null));
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(4)).test(null));
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_LESS_THAN, null).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_LESS_THAN, null).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal(null)).test(null));
    }

    @Test
    void stringTest() {
        assertTrue(new BinaryComparisonOperator(new Literal("a"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("b")).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal("b"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("a")).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal("a"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("A")).test(null));
        assertTrue(new BinaryComparisonOperator(new Literal("A"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("a")).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal("A"), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("a")).setMatchCase(false).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new Literal("a")).test(null));
    }

    @Test
    void greaterThanTest() {
        assertFalse(new BinaryComparisonOperator(new Literal(5.9999), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(6)).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(0), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(0.1)).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(6)).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal((byte)5), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(6l)).test(null));
        assertTrue(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(4)).test(null));
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(4)).test(null));
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_GREATER_THAN, null).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, null).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_GREATER_THAN, new Literal(null)).test(null));
    }

    @Test
    void equalTest() {
        assertFalse(new BinaryComparisonOperator(new Literal(5.9999), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(6)).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(0), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(0.1)).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(6)).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal((byte)5), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(6l)).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(5), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(4)).test(null));
        assertFalse(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(4)).test(null));
        assertTrue(new BinaryComparisonOperator(null, BinaryComparisonName.PROPERTY_IS_EQUAL_TO, null).test(null));
        assertFalse(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, null).test(null));
        assertTrue(new BinaryComparisonOperator(new Literal(null), BinaryComparisonName.PROPERTY_IS_EQUAL_TO, new Literal(null)).test(null));
    }

    @Test
    void featureTests() {
        FeatureType featureType = new FeatureType("test", "MyFeature");
        featureType.putProperty(new FeatureProperty("hazard-type", FeaturePropertyType.DOUBLE));
        
        Feature feature = featureType.createInstance();
        feature.setPropertyValue("hazard-type", 10.0);

        BinaryComparisonOperator filter = new BinaryComparisonOperator(new Literal(5.9999), BinaryComparisonName.PROPERTY_IS_LESS_THAN, new ValueReference("hazard-type"));
        assertTrue(filter.test(feature));
    }

}
