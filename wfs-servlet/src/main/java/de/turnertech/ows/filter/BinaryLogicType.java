package de.turnertech.ows.filter;

import java.util.function.BiPredicate;

public enum BinaryLogicType implements BiPredicate<Boolean, Boolean> {

    AND("And"),
    OR("Or");

    private final String xmlName;

    private BinaryLogicType(final String xmlName) {
        this.xmlName = xmlName;
    }

    @Override
    public boolean test(Boolean leftOperand, Boolean rightOperand) {
        switch(this) {
            case AND:
                return leftOperand && rightOperand;
            case OR:
                return leftOperand || rightOperand;  
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return xmlName;
    }
}
