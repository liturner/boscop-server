package de.turnertech.ows.filter;

import java.util.function.BiPredicate;

public enum BinaryLogicType implements BiPredicate<Operator, Operator> {

    AND("And"),
    OR("Or");

    private final String xmlName;

    private BinaryLogicType(final String xmlName) {
        this.xmlName = xmlName;
    }

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

    @Override
    public String toString() {
        return xmlName;
    }
}
