package de.turnertech.ows.filter;

import de.turnertech.ows.gml.IFeature;

public class NullOperator implements ComparisonOperator {
    
    private final Expression expression;

    public NullOperator(final Expression expression) {
        this.expression = expression;
    }

    /**
     * @return the expression
     */
    public Expression getExpression() {
        return expression;
    }

    @Override
    public boolean test(IFeature feature) {
        return expression.apply(feature) == null;
    }
    
}
