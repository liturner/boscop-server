package de.turnertech.ows.filter;

public class NilOperator extends ComparisonOperator {
    
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
    public boolean getAsBoolean() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAsBoolean'");
    }

}