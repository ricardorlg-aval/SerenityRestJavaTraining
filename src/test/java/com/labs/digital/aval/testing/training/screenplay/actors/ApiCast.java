package com.labs.digital.aval.testing.training.screenplay.actors;

import com.labs.digital.aval.testing.training.utils.TestEnvironment;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

public class ApiCast extends Cast {
    private final TestEnvironment testEnvironment;

    public ApiCast(EnvironmentVariables environmentVariables) {
        this.testEnvironment = new TestEnvironment(environmentVariables);
    }

    @Override
    public Actor actorNamed(String actorName, Ability... abilities) {
        return super.actorNamed(actorName, CallAnApi.at(testEnvironment.getBaseUrl()))
                .describedAs("An Employee Manager API user");
    }

    @Override
    public void dismissAll() {
        super.dismissAll();
        SerenityRest.reset();
    }
}
