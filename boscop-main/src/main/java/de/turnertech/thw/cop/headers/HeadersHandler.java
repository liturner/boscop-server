package de.turnertech.thw.cop.headers;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;

public class HeadersHandler extends RewriteHandler {
    public HeadersHandler() {
        this.addRule(new CacheRule());
        this.addRule(new ContentTypeRule());
    }
}
