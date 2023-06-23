package de.turnertech.ows.filter;

public class BetweenComparisonOperator extends ComparisonOperator {

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
    public boolean getAsBoolean() {
        // TODO Auto-generated method stub
        // Feels like here we can use the Comparable interface?
        throw new UnsupportedOperationException("Unimplemented method 'getAsBoolean'");
    }
    
}
