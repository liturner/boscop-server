package de.turnertech.ows.filter;

import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Objects;

import de.turnertech.ows.gml.DirectPositionList;
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
        return bbox.contains(point.getPos());
    }

    private boolean bbox(final Polygon polygon, final Envelope bbox) {
        if(!bbox.intersects(polygon.getBoundingBox())) return false;

        Path2D exterior = new Path2D.Double();
        DirectPositionList posList = polygon.getExterior().getPosList();
        int i = 1;

        exterior.moveTo(posList.get(0).getX(), posList.get(0).getY());
        do {
            exterior.lineTo(posList.get(i++).getX(), posList.get(i++).getY());
        } while (i < posList.size());

        Area area = new Area(exterior);

        return area.intersects(bbox);
    }

    private boolean bbox(final LineString lineString, final Envelope bbox) {
        for(Line2D line : lineString) {
            if(bbox.intersectsLine(line)) return true;
        }
        return false;
    }

}
