package de.turnertech.ows.filter;

public class LikeOperator extends ComparisonOperator {
    
    private final Expression[] expression = new Expression[2];

    private Character wildCard;

    private Character singleChar;

    private Character escapeChar;

    /**
     * @return the expression
     */
    public Expression[] getExpression() {
        return expression;
    }

    /**
     * @return the wildCard
     */
    public Character getWildCard() {
        return wildCard;
    }

    /**
     * @param wildCard the wildCard to set
     */
    public void setWildCard(Character wildCard) {
        this.wildCard = wildCard;
    }

    /**
     * @return the singleChar
     */
    public Character getSingleChar() {
        return singleChar;
    }

    /**
     * @param singleChar the singleChar to set
     */
    public void setSingleChar(Character singleChar) {
        this.singleChar = singleChar;
    }

    /**
     * @return the escapeChar
     */
    public Character getEscapeChar() {
        return escapeChar;
    }

    /**
     * @param escapeChar the escapeChar to set
     */
    public void setEscapeChar(Character escapeChar) {
        this.escapeChar = escapeChar;
    }

    @Override
    public boolean getAsBoolean() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAsBoolean'");
    }

    

}
