package de.turnertech.thw.cop.headers;

import java.io.IOException;

import org.eclipse.jetty.rewrite.handler.Rule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ContentTypeRule extends Rule {
    
    @Override
    public String matchAndApply(String target, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException
    {
        if(target.endsWith(".css")) {
            httpServletResponse.setHeader("Content-Type", "text/css; charset=utf-8");
        } else if(target.endsWith(".js")) {
            httpServletResponse.setHeader("Content-Type", "text/javascript; charset=utf-8");
        } else if(target.equals("/")) {
            httpServletResponse.setHeader("Content-Type", "text/html; charset=utf-8");
        }

        return null;
    }
}