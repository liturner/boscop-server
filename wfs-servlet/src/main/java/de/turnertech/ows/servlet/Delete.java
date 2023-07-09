package de.turnertech.ows.servlet;

import de.turnertech.ows.filter.Filter;

public class Delete implements TransactionAction {
    
    private Filter filter;

    private String handle;

    /**
     * @return the filter
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public String getHandle() {
        return handle;
    }

    /**
     * @param handle the handle to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }
    
}
