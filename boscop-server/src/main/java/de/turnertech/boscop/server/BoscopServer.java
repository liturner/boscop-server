package de.turnertech.boscop.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import org.glassfish.embeddable.CommandResult;
import org.glassfish.embeddable.CommandRunner;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;

public class BoscopServer {

    public static void main(String[] args) throws IOException {

        File war = new File("C:\\Users\\lukei\\Source\\THW\\BOSCOP\\boscop-server\\boscop-application\\target\\boscop-application-1.0-SNAPSHOT.war");
        GlassFish glassfish = null;
        GlassFishRuntime glassfishRuntime = null;
        try {
            glassfishRuntime = GlassFishRuntime.bootstrap();

            GlassFishProperties glassfishProperties = new GlassFishProperties();
            glassfishProperties.setPort("http-listener", 8080);

            glassfish = glassfishRuntime.newGlassFish(glassfishProperties);
            glassfish.start();

            CommandRunner commandRunner = glassfish.getCommandRunner();

            // CRITICAL! Seperate parameters using seperate strings. The handling from space vs String is not intuitive....
            String command = "create-jdbc-connection-pool";
            String className = "--datasourceclassname=org.postgresql.ds.PGConnectionPoolDataSource";
            String resourceType = "--restype=javax.sql.ConnectionPoolDataSource";
            String def = "--property=user=postgres:password=Tornado_2022:databaseName=boscop:serverName=weich-nas:portNumber=5439";
            CommandResult commandResult = commandRunner.run(command, className, resourceType, def, "PostgresPool");
            if(!(commandResult.getExitStatus() == commandResult.getExitStatus().SUCCESS)) {
                System.out.print(commandResult.getOutput());
                commandResult.getFailureCause().printStackTrace();
                return;
            }

            // CRITICAL! Seperate parameters using seperate strings. The handling from space vs String is not intuitive....
            command = "create-jdbc-resource";
            String poolid = "--connectionpoolid=PostgresPool";
            String dbname = "jdbc/boscop";
            commandResult = commandRunner.run(command, poolid, dbname);
            if(!(commandResult.getExitStatus() == commandResult.getExitStatus().SUCCESS)) {
                System.out.print(commandResult.getOutput());
                commandResult.getFailureCause().printStackTrace();
                return;
            }

            Deployer deployer = glassfish.getDeployer();
            deployer.deploy(war, new String[]{"--force=true", "--contextroot=/"});
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Press Enter to stop server");
        // wait for Enter
        new BufferedReader(new java.io.InputStreamReader(System.in)).readLine();
        try {
            glassfish.dispose();
            glassfishRuntime.shutdown();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
