package de.turnertech.ows.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import de.turnertech.ows.gml.DirectPosition;
import de.turnertech.ows.gml.Envelope;
import de.turnertech.ows.gml.Feature;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.FeaturePropertyType;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.gml.Point;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class BinarySpatialOperatorTests {
    
    private static final String GEOMETRY = "g";

    private static final Predicate<IFeature> bboxPredicate = new BinarySpatialOperator(null, SpatialOperatorName.BBOX, new SpatialDescription(new Envelope(new DirectPosition(-5, -5), new DirectPosition(5, 5))));

    @Test
    void pointTests() {
        final FeatureType pointType = new FeatureType("namespace", "PointType");
        pointType.setSrs(SpatialReferenceSystem.CRS84);
        pointType.setTitle("PointType");
        pointType.putProperty(new FeatureProperty(GEOMETRY, FeaturePropertyType.POINT));
        final Feature pointFeature = pointType.createInstance();
        final Point point = new Point(0, 0);
        pointFeature.setPropertyValue(GEOMETRY, point);

        assertTrue(bboxPredicate.test(pointFeature));
        point.setX(-5.0000001);
        assertFalse(bboxPredicate.test(pointFeature));
        point.setX(-5.0);
        assertTrue(bboxPredicate.test(pointFeature));
        point.setX(-4.999999);
        assertTrue(bboxPredicate.test(pointFeature));
        point.setX(Double.NEGATIVE_INFINITY);
        assertFalse(bboxPredicate.test(pointFeature));
        point.setX(Double.NaN);
        assertFalse(bboxPredicate.test(pointFeature));
        point.setX(5.0000001);
        assertFalse(bboxPredicate.test(pointFeature));
        point.setX(5.0);
        assertTrue(bboxPredicate.test(pointFeature));
        point.setX(4.999999);
        assertTrue(bboxPredicate.test(pointFeature));
        point.setX(Double.POSITIVE_INFINITY);
        assertFalse(bboxPredicate.test(pointFeature));

        point.setX(0);

        point.setY(-5.0000001);
        assertFalse(bboxPredicate.test(pointFeature));
        point.setY(-5.0);
        assertTrue(bboxPredicate.test(pointFeature));
        point.setY(-4.999999);
        assertTrue(bboxPredicate.test(pointFeature));
        point.setY(Double.NEGATIVE_INFINITY);
        assertFalse(bboxPredicate.test(pointFeature));
        point.setY(Double.NaN);
        assertFalse(bboxPredicate.test(pointFeature));
        point.setY(5.0000001);
        assertFalse(bboxPredicate.test(pointFeature));
        point.setY(5.0);
        assertTrue(bboxPredicate.test(pointFeature));
        point.setY(4.999999);
        assertTrue(bboxPredicate.test(pointFeature));
        point.setY(Double.POSITIVE_INFINITY);
        assertFalse(bboxPredicate.test(pointFeature));
    }

    @Test
    void polygonTests() {
        final FeatureType polygonType = new FeatureType("namespace", "PolygonType");
        polygonType.setSrs(SpatialReferenceSystem.CRS84);
        polygonType.setTitle("PolygonType");
        polygonType.putProperty(new FeatureProperty(GEOMETRY, FeaturePropertyType.POLYGON));
        final Feature polygonFeature = polygonType.createInstance();
    }

}
