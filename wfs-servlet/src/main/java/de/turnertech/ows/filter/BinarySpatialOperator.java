package de.turnertech.ows.filter;

import de.turnertech.ows.gml.IFeature;

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
    public boolean test(IFeature t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'test'");
    }
    
}
