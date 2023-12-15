package de.turnertech.thw.cop;

public class OPTA {
    
    private OPTA() {
        
    }

    public static boolean isValid(final String opta) {
        return isValidLength(opta);
    }

    public static boolean isValidLength(final String opta) {
        return (opta != null && opta.length() < 25);
    }

}
