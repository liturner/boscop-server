package de.turnertech.ows.filter;

import java.util.List;
import java.util.Objects;

import de.turnertech.ows.gml.DirectPosition;
import de.turnertech.ows.gml.Envelope;
import de.turnertech.ows.gml.FeatureProperty;
import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.gml.LineString;
import de.turnertech.ows.gml.Point;
import de.turnertech.ows.gml.Polygon;

public class BinarySpatialOperator implements SpatialOperator {

    protected final ValueReference operand1;

    protected final SpatialOperatorName operatorType;

    protected final SpatialDescription operand2;
    
    public BinarySpatialOperator(final ValueReference operand1, final SpatialOperatorName operatorType, final SpatialDescription operand2) {
        this.operand1 = operand1;
        this.operatorType = operatorType;
        this.operand2 = operand2;
    }

    @Override
    public boolean test(IFeature feature) {
        final List<FeatureProperty> geometryProperties = feature.getFeatureType().getBoundingBoxProperties();
        if(geometryProperties.size() == 0) 
            return false;

        if(SpatialOperatorName.BBOX.equals(operatorType)) {
            if(this.operand2 == null || this.operand2.getEnvelope() == null) {
                throw new UnsupportedOperationException("BBOX cannot be used without an Envelope.");
            }

            for(FeatureProperty geometryProperty : geometryProperties) {
                final Object propertyValue = Objects.requireNonNull(feature.getPropertyValue(geometryProperty.getName()));

                switch (geometryProperty.getPropertyType()) {
                    case LINE_STRING:
                        return bbox((LineString)propertyValue, operand2.getEnvelope());
                    case POINT:
                    case POINT_TYPE:
                        return bbox((Point)propertyValue, operand2.getEnvelope());
                    case POLYGON:
                    case POLYGON_TYPE:
                        return bbox((Polygon)propertyValue, operand2.getEnvelope());
                    default:
                        throw new UnsupportedOperationException("Unimplemented");
                }
            }
        }
        
        throw new UnsupportedOperationException("Unimplemented");
    }
    
    private boolean bbox(final Point point, final Envelope bbox) {
        return bbox.contains(point.getY(), point.getX());
    }

    private boolean bbox(final Polygon polygon, final Envelope bbox) {
        throw new UnsupportedOperationException("Unimplemented");
    }

    private boolean bbox(final LineString line, final Envelope bbox) {
        throw new UnsupportedOperationException("Unimplemented");
    }

    private boolean bbox(final DirectPosition p1, final DirectPosition p2, final Envelope bbox) {
        
        TODO
        
        Get the individual lines of the Envelope and check intersection
        
        return doIntersect(p1, p1, p2, p2)

        throw new UnsupportedOperationException("Unimplemented");
    }

    // Given three collinear points p, q, r, the function checks if
    // point q lies on line segment 'pr'
    static boolean onSegment(DirectPosition p, DirectPosition q, DirectPosition r) {
        return (q.getX() <= Math.max(p.getX(), r.getX()) && 
                q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && 
                q.getY() >= Math.min(p.getY(), r.getY()));
    }
    
    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(DirectPosition p, DirectPosition q, DirectPosition r)
    {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());
    
        if (val == 0) return 0; // collinear
    
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }
    
    // The main function that returns true if line segment 'p1q1'
    // and 'p2q2' intersect.
    static boolean doIntersect(DirectPosition p1, DirectPosition q1, DirectPosition p2, DirectPosition q2)
    {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);
    
        // General case
        if (o1 != o2 && o3 != o4)
            return true;
    
        // Special Cases
        // p1, q1 and p2 are collinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;
    
        // p1, q1 and q2 are collinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;
    
        // p2, q2 and p1 are collinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;
    
        // p2, q2 and q1 are collinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;
    
        return false; // Doesn't fall in any of the above cases
    }
}
