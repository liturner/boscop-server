package de.turnertech.ows.filter;

import de.turnertech.ows.gml.IFeature;

public class NilOperator implements ComparisonOperator {
    
    private Expression expression;

    /**
     * @return the expression
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public boolean test(IFeature feature) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAsBoolean'");
    }

}
