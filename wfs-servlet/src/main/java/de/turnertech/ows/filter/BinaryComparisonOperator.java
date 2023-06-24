package de.turnertech.ows.filter;

import de.turnertech.ows.gml.IFeature;

public class BinaryComparisonOperator implements ComparisonOperator {

    private final Expression leftExpression;

    private final BinaryComparisonName operatorType;

    private final Expression rightExpression;

    private boolean matchCase;

    private MatchAction matchAction;

    public BinaryComparisonOperator(final Expression leftExpression, BinaryComparisonName operatorType, final Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.operatorType = operatorType;
        this.rightExpression = rightExpression;
        this.matchCase = true;
        this.matchAction = MatchAction.ANY;
    }
    
    public Expression getLeftExpression() {
        return leftExpression;
    }

    public BinaryComparisonName getOperatorType() {
        return operatorType;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    public boolean isMatchCase() {
        return matchCase;
    }

    public BinaryComparisonOperator setMatchCase(boolean matchCase) {
        this.matchCase = matchCase;
        return this;
    }

    public MatchAction getMatchAction() {
        return matchAction;
    }

    public BinaryComparisonOperator setMatchAction(MatchAction matchAction) {
        this.matchAction = matchAction;
        return this;
    }

    @Override
    public boolean test(IFeature feature) {
        // Special case handling for null.
        if(leftExpression == null && rightExpression == null && operatorType.equals(BinaryComparisonName.PROPERTY_IS_EQUAL_TO)) {
            return true;
        }
        if(leftExpression == null || rightExpression == null) {
            return false;
        }

        final Object left = leftExpression.apply(feature);
        final Object right = rightExpression.apply(feature);

        return operatorType.test(left, right, matchCase);
    }
    
}
