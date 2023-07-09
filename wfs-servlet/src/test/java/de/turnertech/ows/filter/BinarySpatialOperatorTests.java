package de.turnertech.ows.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.turnertech.ows.gml.DirectPosition;
import de.turnertech.ows.gml.DirectPositionList;
import de.turnertech.ows.gml.Envelope;
import de.turnertech.ows.gml.Feature;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.FeaturePropertyType;
import de.turnertech.ows.gml.FeatureType;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.gml.LineString;
import de.turnertech.ows.gml.Point;
import de.turnertech.ows.srs.SpatialReferenceSystem;

public class BinarySpatialOperatorTests {
    
    private static final String GEOMETRY = "g";

    private static final Predicate<IFeature> bboxPredicate = new BinarySpatialOperator(null, SpatialOperatorName.BBOX, new SpatialDescription(new Envelope(new DirectPosition(-5, -5), new DirectPosition(5, 5))));

    private static FeatureType POINT_TYPE;

    private static FeatureType LINE_STRING_TYPE;

    @BeforeAll
    static void setup() {
        POINT_TYPE = new FeatureType("namespace", "PointType");
        POINT_TYPE.putProperty(new FeatureProperty(GEOMETRY, FeaturePropertyType.POINT));

        LINE_STRING_TYPE = new FeatureType("namespace", "LinestringType");
        LINE_STRING_TYPE.putProperty(new FeatureProperty(GEOMETRY, FeaturePropertyType.LINE_STRING));
    }

    @Test
    void pointXTests() {
        final Feature pointFeature = POINT_TYPE.createInstance();
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
    }

    @Test
    void pointYTests() {
        final Feature pointFeature = POINT_TYPE.createInstance();
        final Point point = new Point(0, 0);
        pointFeature.setPropertyValue(GEOMETRY, point);

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

    @Test void lineTests() {
        final Feature feature = LINE_STRING_TYPE.createInstance();
        final DirectPosition p0 = new DirectPosition(0, 0);
        final DirectPosition p1 = new DirectPosition(1, 1);
        final LineString line = new LineString(new DirectPositionList(p0, p1));
        feature.setPropertyValue(GEOMETRY, line);

        assertTrue(bboxPredicate.test(feature));
        p0.setLocation(-5, -5);
        assertTrue(bboxPredicate.test(feature));
        p1.setLocation(-5, 0);
        assertTrue(bboxPredicate.test(feature));
        p1.setLocation(-6, -6);
        assertTrue(bboxPredicate.test(feature));
        p0.setLocation(-5.0000000001, 0);
        assertFalse(bboxPredicate.test(feature));
        p1.setLocation(6, 0);
        assertTrue(bboxPredicate.test(feature));
        p0.setLocation(-1000, 5);
        p1.setLocation(1000, 5);
        assertTrue(bboxPredicate.test(feature));
        p1.setLocation(1000, 5.0000001);
        assertFalse(bboxPredicate.test(feature));
        p1.setLocation(1000, 4.9999999);
        assertTrue(bboxPredicate.test(feature));
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
