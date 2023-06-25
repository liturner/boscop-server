package de.turnertech.ows.filter;

import de.turnertech.ows.gml.IFeature;

public class BetweenComparisonOperator implements ComparisonOperator {

    private final Expression lowerBoundary;

    private final Expression expression;

    private final Expression upperBoundary;

    public BetweenComparisonOperator(final Expression lowerBoundary, final Expression expression, final Expression upperBoundary) {
        this.lowerBoundary = lowerBoundary;
        this.expression = expression;
        this.upperBoundary = upperBoundary;
    }

    /**
     * @return the expression
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * @return the lowerBoundary
     */
    public Expression getLowerBoundary() {
        return lowerBoundary;
    }

    /**
     * @return the upperBoundary
     */
    public Expression getUpperBoundary() {
        return upperBoundary;
    }

    @Override
    public boolean test(IFeature feature) {
        final Object testValue = expression.apply(feature);
        return BinaryComparisonName.PROPERTY_IS_GREATER_THAN_OR_EQUAL_TO.test(testValue, lowerBoundary.apply(feature)) &&
                BinaryComparisonName.PROPERTY_IS_LESS_THAN_OR_EQUAL_TO.test(testValue, upperBoundary.apply(feature));
    }
    
}
