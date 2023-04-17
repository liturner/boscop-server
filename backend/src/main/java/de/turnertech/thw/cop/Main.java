package de.turnertech.thw.cop;

import java.util.Set;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.security.authentication.DigestAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;

public class Main {
    
    public static final String REALM = "thwCopRealm";

    public class Roles {
        public static final String USER = "user";
        public static final String ADMIN = "admin";
    }

    public static void main(String[] args) {
        Server server = new Server(8080);

        // See: https://www.programcreek.com/java-api-examples/?api=org.eclipse.jetty.security.ConstraintSecurityHandler
        HashLoginService loginService = new HashLoginService();
        loginService.setName(REALM);
        loginService.setConfig(Main.class.getResource("/users.txt").toString());

        Constraint constraint = new Constraint(Constraint.__DIGEST_AUTH, Roles.USER);
        //constraint.setName(Constraint.__BASIC_AUTH);
        //		constraint.setRoles(new String[] { "getRole", "postRole", "allRole" });
        //constraint.setRoles(new String[]{Constraint.ANY_AUTH, "getRole", "postRole", "allRole"});
        constraint.setAuthenticate(true);

        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setConstraint(constraint);
        constraintMapping.setPathSpec("/*");

        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.setAuthenticator(new DigestAuthenticator());
        securityHandler.setRealmName(REALM);
        securityHandler.setLoginService(loginService);
        securityHandler.addRole(Roles.ADMIN);
        securityHandler.addRole(Roles.USER);
        securityHandler.addConstraintMapping(constraintMapping);

        ExampleServlet copServlet = new ExampleServlet();
        ServletHolder copServletHolder = new ServletHolder(copServlet);
        
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(copServletHolder, "/cop");        
        handler.setSecurityHandler(securityHandler);

        server.addBean(loginService);
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
