package com.labs.digital.aval.testing.training.utils;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;

public final class TestEnvironment {
    private final EnvironmentVariables environmentVariables;

    public TestEnvironment(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public String getBaseUrl() {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("restapi.baseurl");
    }
}
