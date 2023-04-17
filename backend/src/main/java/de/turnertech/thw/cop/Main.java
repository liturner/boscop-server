package de.turnertech.thw.cop;

import java.util.Set;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;

public class Main {
    
    public static void main(String[] args) {
        Server server = new Server(8080);

        // See: https://www.programcreek.com/java-api-examples/?api=org.eclipse.jetty.security.ConstraintSecurityHandler
        HashLoginService loginService = new HashLoginService();
        loginService.setName("thwCopRealm");
        loginService.setConfig(Main.class.getResource("/thwCopRealm.txt").toString());

        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__BASIC_AUTH);
        //		constraint.setRoles(new String[] { "getRole", "postRole", "allRole" });
        constraint.setRoles(new String[]{Constraint.ANY_AUTH, "getRole", "postRole", "allRole"});
        constraint.setAuthenticate(true);

        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setConstraint(constraint);
        constraintMapping.setPathSpec("/*");

        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.setAuthenticator(new BasicAuthenticator());
        securityHandler.setRealmName("thwCopRealm");
        securityHandler.setLoginService(loginService);
        securityHandler.addRole("admin");
        securityHandler.addRole("user");
        securityHandler.addRole("tracker");
        securityHandler.addConstraintMapping(constraintMapping);

        ExampleServlet copServlet = new ExampleServlet();
        ServletHolder copServletHolder = new ServletHolder(copServlet);
        
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(copServletHolder, "/cop");        
        handler.setSecurityHandler(securityHandler);

        server.setHandler(handler);  
        
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
