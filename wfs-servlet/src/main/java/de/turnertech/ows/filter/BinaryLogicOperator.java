package de.turnertech.ows.filter;

import java.util.Objects;

public class BinaryLogicOperator extends LogicalOperator {

    private final NonIdOperator leftOperand;

    private final BinaryLogicType operatorType;

    private final NonIdOperator rightOperand;

    public BinaryLogicOperator(final NonIdOperator leftOperand, final BinaryLogicType operatorType, final NonIdOperator rightOperand) {
        this.leftOperand = Objects.requireNonNull(leftOperand);
        this.operatorType = Objects.requireNonNull(operatorType);
        this.rightOperand = Objects.requireNonNull(rightOperand);
    }

    /**
     * @return the operatorType
     */
    public BinaryLogicType getOperatorType() {
        return operatorType;
    }

    public NonIdOperator getLeftOperand() {
        return leftOperand;
    }

    public NonIdOperator getRightOperand() {
        return rightOperand;
    }

    @Override
    public boolean getAsBoolean() {
        return operatorType.test(leftOperand, rightOperand);
    }

}
