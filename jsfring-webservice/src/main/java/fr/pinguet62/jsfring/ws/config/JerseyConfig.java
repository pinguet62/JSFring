package fr.pinguet62.jsfring.ws.config;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("fr.pinguet62.jsfring");
    }

}