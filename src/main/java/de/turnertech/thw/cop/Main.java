package de.turnertech.thw.cop;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.DigestAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Constraint;

import de.turnertech.thw.cop.headers.HeadersHandler;

public class Main {
    
    public static final String REALM = "urn:de:turnertech:cop";

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

        Constraint constraintDigest = new Constraint(Constraint.__DIGEST_AUTH, Roles.USER);
        constraintDigest.setAuthenticate(true);

        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setConstraint(constraintDigest);
        constraintMapping.setPathSpec("/*");

        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.setAuthenticator(new DigestAuthenticator());
        securityHandler.setRealmName(REALM);
        securityHandler.setLoginService(loginService);
        securityHandler.addRole(Roles.ADMIN);
        securityHandler.addRole(Roles.USER);
        securityHandler.addConstraintMapping(constraintMapping);

        CopServlet copServlet = new CopServlet();
        ServletHolder copServletHolder = new ServletHolder(copServlet);
        
        TokenServlet tokenServlet = new TokenServlet();
        ServletHolder tokenServletHolder = new ServletHolder(tokenServlet);

        TrackerServlet trackerServlet = new TrackerServlet();
        ServletHolder trackerServletHolder = new ServletHolder(trackerServlet);

        ServletHolder defaultServletHolder = new ServletHolder("default", DefaultServlet.class);
        defaultServletHolder.setInitParameter("dirAllowed","true");

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setBaseResource(Resource.newClassPathResource("webapp"));
        contextHandler.addServlet(defaultServletHolder, "/");
        contextHandler.addServlet(copServletHolder, "/cop");
        contextHandler.addServlet(tokenServletHolder, "/token");
        contextHandler.addServlet(trackerServletHolder, "/tracker");        
        //contextHandler.setSecurityHandler(securityHandler); Uncomment to add Auth again

        HeadersHandler headerHandler = new HeadersHandler();
        headerHandler.setHandler(contextHandler);

        server.addBean(loginService);
        server.setHandler(headerHandler);
        
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
