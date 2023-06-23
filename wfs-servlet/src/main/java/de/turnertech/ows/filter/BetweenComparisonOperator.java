package de.turnertech.ows.filter;

public class BetweenComparisonOperator extends ComparisonOperator {
    
    private Expression expression;

    private Expression lowerBoundary;

    private Expression upperBoundary;

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

    /**
     * @return the lowerBoundary
     */
    public Expression getLowerBoundary() {
        return lowerBoundary;
    }

    /**
     * @param lowerBoundary the lowerBoundary to set
     */
    public void setLowerBoundary(Expression lowerBoundary) {
        this.lowerBoundary = lowerBoundary;
    }

    /**
     * @return the upperBoundary
     */
    public Expression getUpperBoundary() {
        return upperBoundary;
    }

    /**
     * @param upperBoundary the upperBoundary to set
     */
    public void setUpperBoundary(Expression upperBoundary) {
        this.upperBoundary = upperBoundary;
    }
    
}
