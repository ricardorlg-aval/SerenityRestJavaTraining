package com.labs.digital.aval.testing.training.screenplay.questions;

import com.labs.digital.aval.testing.training.models.AllEmployeeResponse;
import com.labs.digital.aval.testing.training.models.EmployeeInformation;
import com.labs.digital.aval.testing.training.screenplay.tasks.GetEmployeeInformation;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;

public class TheEmployee implements Question<EmployeeInformation> {
    private final String employeeId;
    private final boolean fetchingDatabase;

    public TheEmployee(String employeeId, boolean fetchingDatabase) {
        this.employeeId = employeeId;
        this.fetchingDatabase = fetchingDatabase;
    }

    public TheEmployee fromDatabase() {
        return new TheEmployee(employeeId, true);
    }

    @Subject("The Employee with id #employeeId")
    @Override
    public EmployeeInformation answeredBy(Actor actor) {
        if (fetchingDatabase) actor.wasAbleTo(GetEmployeeInformation.forAllEmployees());
        actor.should(
                ResponseConsequence.seeThatResponse("All employees response should be 200", response -> response.statusCode(200))
        );
        AllEmployeeResponse employees = actor.asksFor(LastResponse.received()).as(AllEmployeeResponse.class);

        return employees.getData().stream().filter(employee -> employee.getId().equals(employeeId)).findFirst().orElse(null);
    }

    public static TheEmployee withId(String id) {
        return new TheEmployee(id, false);
    }
}
