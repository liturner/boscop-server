package de.turnertech.ows.filter;

public class Literal extends Expression {

    private final Object value;

    public Literal (final Object value) {
        this.value = value;
    }

    @Override
    public Object get() {
        return value;
    }

}
