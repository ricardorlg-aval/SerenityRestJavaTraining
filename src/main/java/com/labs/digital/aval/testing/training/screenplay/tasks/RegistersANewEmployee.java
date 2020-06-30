package com.labs.digital.aval.testing.training.screenplay.tasks;

import com.labs.digital.aval.testing.training.endPoints.DummyApiEndPoints;
import com.labs.digital.aval.testing.training.models.EmployeeInformation;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegistersANewEmployee implements Task {

    private final EmployeeInformation data;

    public RegistersANewEmployee(EmployeeInformation data) {
        this.data = data;
    }

    @Step("{0} attempts to register a new information for an employee")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(DummyApiEndPoints.CREATE_EMPLOYEE_END_POINT.getRoute()).with(request -> request.body(data).contentType(ContentType.JSON))
        );
    }

    public static RegistersANewEmployee withInformation(EmployeeInformation data) {
        return instrumented(RegistersANewEmployee.class, data);
    }
}
