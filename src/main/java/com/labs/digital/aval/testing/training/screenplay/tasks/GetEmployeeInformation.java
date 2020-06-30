package com.labs.digital.aval.testing.training.screenplay.tasks;

import com.labs.digital.aval.testing.training.endPoints.DummyApiEndPoints;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetEmployeeInformation implements Task {
    @Step("{0} gets the information of all registered employees")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(DummyApiEndPoints.GET_ALL_EMPLOYEE.getRoute()).withRequest(request -> request.contentType(ContentType.JSON))
        );
    }

    public static GetEmployeeInformation forAllEmployees() {
        return instrumented(GetEmployeeInformation.class);
    }
}
