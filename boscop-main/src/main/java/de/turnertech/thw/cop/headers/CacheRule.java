package de.turnertech.thw.cop.headers;

import java.io.IOException;

import org.eclipse.jetty.rewrite.handler.Rule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CacheRule extends Rule {
    
    @Override
    public String matchAndApply(String target, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException
    {
        // By default, cache nothing.
        httpServletResponse.setHeader("Cache-Control", "no-store");

        // Cache OpenLayers for a few weeks
        if(target.contains("ol.js")) {
            httpServletResponse.setHeader("Cache-Control", "max-age=1209600");
        }

        return null;
    }
}
