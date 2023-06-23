package de.turnertech.ows.filter;

import java.util.function.BiPredicate;

public enum BinaryLogicType implements BiPredicate<Operator, Operator> {

    AND,
    OR;

    @Override
    public boolean test(Operator leftOperand, Operator rightOperand) {
        switch(this) {
            case AND:
                return leftOperand.getAsBoolean() && rightOperand.getAsBoolean();
            case OR:
                return leftOperand.getAsBoolean() || rightOperand.getAsBoolean();  
            default:
                return false;
        }
    }
}
