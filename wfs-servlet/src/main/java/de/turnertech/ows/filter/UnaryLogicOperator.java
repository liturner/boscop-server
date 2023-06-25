package de.turnertech.ows.filter;

public class UnaryLogicOperator {
    
    private NonIdOperator operands;

    private UnaryLogicType operatorType;

    /**
     * @return the operands
     */
    public NonIdOperator getOperands() {
        return operands;
    }

    /**
     * @param operands the operands to set
     */
    public void setOperands(NonIdOperator operands) {
        this.operands = operands;
    }

    /**
     * @return the operatorType
     */
    public UnaryLogicType getOperatorType() {
        return operatorType;
    }

    /**
     * @param operatorType the operatorType to set
     */
    public void setOperatorType(UnaryLogicType operatorType) {
        this.operatorType = operatorType;
    }

    

}
