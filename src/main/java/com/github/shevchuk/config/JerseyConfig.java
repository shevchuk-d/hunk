package com.github.shevchuk.config;

import com.github.shevchuk.contorllers.SessionController;
import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
         register(SessionController.class);
    }
}