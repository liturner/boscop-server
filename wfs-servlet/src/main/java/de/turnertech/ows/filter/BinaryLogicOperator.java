package de.turnertech.ows.filter;

public class BinaryLogicOperator extends LogicalOperator {

    private final NonIdOperator[] operands;

    private BinaryLogicType operatorType;

    public BinaryLogicOperator() {
        this.operands = new NonIdOperator[2];
    }

    /**
     * @return the operands
     */
    public NonIdOperator[] getOperands() {
        return operands;
    }

    /**
     * @return the operatorType
     */
    public BinaryLogicType getOperatorType() {
        return operatorType;
    }

    /**
     * @param operatorType the operatorType to set
     */
    public void setOperatorType(BinaryLogicType operatorType) {
        this.operatorType = operatorType;
    }

}
