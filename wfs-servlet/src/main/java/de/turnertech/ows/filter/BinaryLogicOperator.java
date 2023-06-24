package de.turnertech.ows.filter;

import java.util.Objects;

import de.turnertech.ows.gml.IFeature;

public class BinaryLogicOperator implements LogicalOperator {

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
    public boolean test(IFeature feature) {
        return operatorType.test(leftOperand.test(feature), rightOperand.test(feature));
    }

}
