package de.turnertech.ows.filter;

public class NullOperator extends ComparisonOperator {
    
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
    public boolean getAsBoolean() {
        return expression.get() == null;
    }
    
}
