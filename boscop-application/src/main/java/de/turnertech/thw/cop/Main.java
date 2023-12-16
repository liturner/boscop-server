package de.turnertech.thw.cop;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        /**
        Settings.parseArguments(args);

        /**
         * Folder initialisation and basic startup (Order dependant)
         *

        File dataFolder = Settings.getDataDirectory();
        dataFolder.mkdirs();

        File configFolder = Settings.getDataDirectory();
        configFolder.mkdirs();

        File featureTypesFolder = Settings.getFeatureTypeDirectory();
        featureTypesFolder.mkdirs();

        File usersFile = Settings.getUsersFile();
        if(!usersFile.exists()) {
            Logging.LOG.severe(() -> "Could not load Users.txt from the configured boscop.data directory. This file must exist with at least one user!. The current boscop.data directory is: " + Settings.getDataDirectory().toString());
            System.exit(-1);
        }

        File frontendDirectory = Settings.getFrontendDirectory();

        TrackerToken.load();



        // See: https://www.programcreek.com/java-api-examples/?api=org.eclipse.jetty.security.ConstraintSecurityHandler
        HashLoginService loginService = new HashLoginService();
        loginService.setName(Constants.REALM);
        loginService.setConfig(usersFile.toString());

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
        securityHandler.setLoginService(loginService);
        securityHandler.addConstraintMapping(constraintMapping);
        securityHandler.addConstraintMapping(trackerRootResourceConstraintMapping);
        securityHandler.addConstraintMapping(trackerSubResourceConstraintMapping);

        Filter trackerAccessFilter = new TrackerAccessFilter();
        FilterHolder trackerAccessFilterHolder = new FilterHolder(trackerAccessFilter);


        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addFilter(trackerAccessFilterHolder, Constants.Paths.TRACKER_API, EnumSet.of(DispatcherType.REQUEST));
        contextHandler.setSecurityHandler(securityHandler);

        server.addBean(loginService);
        server.setHandler(contextHandler);
**/
    }

}
