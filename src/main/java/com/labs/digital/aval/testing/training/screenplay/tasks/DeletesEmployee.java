package com.labs.digital.aval.testing.training.screenplay.tasks;

import com.labs.digital.aval.testing.training.endPoints.DummyApiEndPoints;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeletesEmployee implements Task {
    private final String employeeId;

    public DeletesEmployee(String employeeId) {
        this.employeeId = employeeId;
    }

    @Step("{0} deletes the information of the employee with ID #employeeId")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from(DummyApiEndPoints.DELETE_EMPLOYEE_ENDPOINT.getRoute()).withRequest(request ->
                        request.contentType(ContentType.JSON)
                                .pathParam("employee_id", employeeId)
                )
        );
    }

    public static DeletesEmployee withId(String employeeId) {
        return instrumented(DeletesEmployee.class, employeeId);
    }
}
