package com.labs.digital.aval.testing.training.features;

import com.labs.digital.aval.testing.training.screenplay.actors.ApiCast;
import com.labs.digital.aval.testing.training.utils.JacksonHelper;
import io.cucumber.java.Before;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.SessionConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.filter.session.SessionFilter;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import net.thucydides.core.util.EnvironmentVariables;

public class Hooks {
    private EnvironmentVariables environmentVariables;

    @Before
    public void setUp() {
        SerenityRest.setDefaultConfig(SerenityRest
                .config()
                .objectMapperConfig(ObjectMapperConfig
                        .objectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> JacksonHelper.getInstance().getObjectMapper()))
                .sessionConfig(
                        SessionConfig
                                .sessionConfig()
                                .sessionIdName("PHPSESSID")
                )
        );
        Filter noCacheFilter = (requestSpec, responseSpec, ctx) -> {
            requestSpec.header("Cache-Control", "no-cache");
            return ctx.next(requestSpec, responseSpec);
        };
        if (isRestLoggingEnabled()) {
            SerenityRest.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new SessionFilter(), noCacheFilter);
        } else {
            SerenityRest.filters(new SessionFilter(), noCacheFilter);
        }
        OnStage.setTheStage(new ApiCast(environmentVariables));
    }

    private Boolean isRestLoggingEnabled() {
        return EnvironmentSpecificConfiguration
                .from(environmentVariables)
                .getOptionalProperty("enable.rest.logging")
                .map(Boolean::valueOf)
                .orElse(false);
    }
}
