package de.turnertech.ows.filter;

import java.util.function.BiPredicate;

public enum BinaryComparisonName implements BiPredicate<Expression, Expression> {
    PROPERTY_IS_EQUAL_TO,
    PROPERTY_IS_NOT_EQUAL_TO,
    PROPERTY_IS_LESS_THAN,
    PROPERTY_IS_GREATER_THAN,
    PROPERTY_IS_LESS_THAN_OR_EQUAL_TO,
    PROPERTY_IS_GREATER_THAN_OR_EQUAL_TO;

    @Override
    public boolean test(Expression leftExpression, Expression rightExpression) {
        Object left = leftExpression.get();
        Object right = rightExpression.get();

        if(left instanceof Comparable) {
            switch(this) {
                case PROPERTY_IS_EQUAL_TO:
                    break;
                case PROPERTY_IS_GREATER_THAN:
                    break;
                case PROPERTY_IS_GREATER_THAN_OR_EQUAL_TO:
                    break;
                case PROPERTY_IS_LESS_THAN:
                    return ((Comparable)left).compareTo(rightExpression) < 0;
                    break;
                case PROPERTY_IS_LESS_THAN_OR_EQUAL_TO:
                    break;
                case PROPERTY_IS_NOT_EQUAL_TO:
                    break;
                default:
                    break;
                
            }
        }
        return false;
    }
}
