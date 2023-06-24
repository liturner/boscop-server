package de.turnertech.ows.filter;

public class ResourceId implements Id {

    private final String rid;

    public ResourceId(final String rid) {
        this.rid = rid;
    }

    /**
     * @return the rid
     */
    public String getRid() {
        return rid;
    }

    @Override
    public String toString() {
        return rid;
    }
}
