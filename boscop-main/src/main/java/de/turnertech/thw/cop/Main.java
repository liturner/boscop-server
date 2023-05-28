package de.turnertech.thw.cop;

import java.io.File;
import java.util.EnumSet;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.DigestAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Constraint;

import de.turnertech.ows.servlet.ErrorServlet;
import de.turnertech.ows.servlet.WfsServlet;
import de.turnertech.thw.cop.Constants.Roles;
import de.turnertech.thw.cop.headers.HeadersHandler;
import de.turnertech.thw.cop.ows.WfsFilter;
import de.turnertech.thw.cop.trackers.TrackerAccessFilter;
import de.turnertech.thw.cop.trackers.TrackerServlet;
import de.turnertech.thw.cop.trackers.TrackerSubServlet;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;

public class Main {
    
    public static void main(String[] args) {
        Settings.parseArguments(args);

        /**
         * Folder initialisation and basic startup
         */

        File dataFolder = Settings.getDataDirectory();
        dataFolder.mkdirs();

        File configFolder = Settings.getDataDirectory();
        configFolder.mkdirs();

        File featureTypesFolder = Settings.getFeatureTypeDirectory();
        featureTypesFolder.mkdirs();
        
        /**
         * Server startup
         */

        Server server = new Server(Settings.getPort());

        // See: https://www.programcreek.com/java-api-examples/?api=org.eclipse.jetty.security.ConstraintSecurityHandler
        HashLoginService loginService = new HashLoginService();
        loginService.setName(Constants.REALM);
        loginService.setConfig(Main.class.getResource("/users.txt").toString());

        Constraint constraintDigest = new Constraint(Constraint.__DIGEST_AUTH, Constants.Roles.USER);
        constraintDigest.setAuthenticate(true);

        Constraint constraintNoAuth = new Constraint();
        constraintNoAuth.setAuthenticate(false);

        // Default auth is DIGEST for everything
        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setConstraint(constraintDigest);
        constraintMapping.setPathSpec("/*");

        ConstraintMapping trackerRootResourceConstraintMapping = new ConstraintMapping();
        trackerRootResourceConstraintMapping.setConstraint(constraintDigest);
        trackerRootResourceConstraintMapping.setPathSpec(Constants.Paths.TRACKER_USER);

        ConstraintMapping trackerSubResourceConstraintMapping = new ConstraintMapping();
        trackerSubResourceConstraintMapping.setConstraint(constraintNoAuth);
        trackerSubResourceConstraintMapping.setPathSpec(Constants.Paths.TRACKER_API);

        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.setAuthenticator(new DigestAuthenticator());
        securityHandler.setRealmName(Constants.REALM);
        securityHandler.setLoginService(loginService);
        securityHandler.addRole(Roles.ADMIN);
        securityHandler.addRole(Roles.USER);
        securityHandler.addConstraintMapping(constraintMapping);
        securityHandler.addConstraintMapping(trackerRootResourceConstraintMapping);
        securityHandler.addConstraintMapping(trackerSubResourceConstraintMapping);

        Filter trackerAccessFilter = new TrackerAccessFilter();
        FilterHolder trackerAccessFilterHolder = new FilterHolder(trackerAccessFilter);

        Filter wfsFilter = new WfsFilter();
        FilterHolder wfsFilterHolder = new FilterHolder(wfsFilter);

        ServletHolder errorServletHolder = new ServletHolder("Error-Servlet", new ErrorServlet());
        ServletHolder tokenServletHolder = new ServletHolder(new TokenServlet());
        ServletHolder trackerServletHolder = new ServletHolder(new TrackerServlet());
        ServletHolder trackerSubServletHolder = new ServletHolder(new TrackerSubServlet());

        ServletHolder wfsServletHolder = new ServletHolder("WFS-Servlet", new WfsServlet());
        wfsServletHolder.setInitParameter(WfsServlet.OWS_CONTEXT_FACTORY_KEY, "de.turnertech.thw.cop.BoscopOwsContextFactory");

        ServletHolder defaultServletHolder = new ServletHolder("default", DefaultServlet.class);
        defaultServletHolder.setInitParameter("dirAllowed","true");

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setBaseResource(Resource.newClassPathResource("webapp"));
        contextHandler.addServlet(defaultServletHolder, "/");
        contextHandler.addServlet(tokenServletHolder, "/token");
        contextHandler.addServlet(errorServletHolder, Constants.Paths.ERROR);        
        contextHandler.addServlet(wfsServletHolder, Constants.Paths.WFS);
        contextHandler.addFilter(wfsFilterHolder, Constants.Paths.WFS, EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addServlet(trackerServletHolder, Constants.Paths.TRACKER_USER);
        contextHandler.addServlet(trackerSubServletHolder, Constants.Paths.TRACKER_API);
        contextHandler.addFilter(trackerAccessFilterHolder, Constants.Paths.TRACKER_API, EnumSet.of(DispatcherType.REQUEST));
        contextHandler.setSecurityHandler(securityHandler);

        ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
        errorHandler.addErrorPage(0, 999, Constants.Paths.ERROR);
        contextHandler.setErrorHandler(errorHandler);

        HeadersHandler headerHandler = new HeadersHandler();
        headerHandler.setHandler(contextHandler);
        
        server.addBean(loginService);
        server.setHandler(headerHandler);
        
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            Logging.LOG.severe("Could not start server.");
        }
    }

}
