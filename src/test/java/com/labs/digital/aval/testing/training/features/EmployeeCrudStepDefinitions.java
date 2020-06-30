package com.labs.digital.aval.testing.training.features;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import com.labs.digital.aval.testing.training.models.EmployeeInformation;
import com.labs.digital.aval.testing.training.screenplay.facts.RegisteredNewEmployee;
import com.labs.digital.aval.testing.training.screenplay.questions.TheEmployee;
import com.labs.digital.aval.testing.training.screenplay.tasks.DeletesEmployee;
import com.labs.digital.aval.testing.training.screenplay.tasks.GetEmployeeInformation;
import com.labs.digital.aval.testing.training.screenplay.tasks.RegistersANewEmployee;
import com.labs.digital.aval.testing.training.utils.Constants;
import com.labs.digital.aval.testing.training.utils.JacksonHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class EmployeeCrudStepDefinitions {

    private final Faker faker = new Faker();

    @Given("{word} wants to register a new Employee information")
    public void actorWantsToRegisterNewInformationForEmployee(String actorName) throws JsonProcessingException {
        String age = String.valueOf(faker.number().numberBetween(18, 45));
        String salary = String.valueOf(faker.number().numberBetween(2000000, 3000000));
        EmployeeInformation employeeInformation = new EmployeeInformation(faker.name().fullName(), age, salary);
        Serenity.recordReportData().withTitle("Employee information to register")
                .andContents(JacksonHelper.getInstance().getObjectMapper().writeValueAsString(employeeInformation));
        OnStage
                .theActorCalled(actorName)
                .remember(Constants.EMPLOYEE_INFORMATION, employeeInformation);
    }

    @Given("{word} has registered a new Employee information")
    public void actorHasRegisteredNewEmployeeInformation(String actorName) {
        OnStage.theActorCalled(actorName).has(RegisteredNewEmployee.withRandomData());
    }

    @When("he/she makes a post with the employee information")
    public void heMakesAPostWithTheEmployeeInformation() {
        EmployeeInformation data = OnStage.theActorInTheSpotlight().recall(Constants.EMPLOYEE_INFORMATION);
        OnStage
                .theActorInTheSpotlight()
                .attemptsTo(
                        RegistersANewEmployee.withInformation(data)
                );
    }

    @Then("the employee should be registered")
    public void theEmployeeShouldBeRegistered() {
        EmployeeInformation expectedData = OnStage.theActorInTheSpotlight().recall(Constants.EMPLOYEE_INFORMATION);
        Actor currentActor = OnStage.theActorInTheSpotlight();
        currentActor.should(
                seeThatResponse("the create employee response status code should be 200", response -> response.statusCode(200))
        );

        currentActor.should("Validate response Data",
                seeThatResponse("Employee ID should not be empty", response -> response.body("id", CoreMatchers.not(Matchers.isEmptyString()))),
                seeThatResponse("Employe name should be the expected", response -> response.body("data.name", CoreMatchers.equalTo(expectedData.getName()))),
                seeThatResponse("Employe age should be the expected", response -> response.body("data.age", CoreMatchers.equalTo(expectedData.getAge()))),
                seeThatResponse("Employe salary should be the expected", response -> response.body("data.salary", CoreMatchers.equalTo(expectedData.getSalary())))
        );
    }

    @When("he/she ask for all the registered employees information")
    public void heAskForAllTheRegisteredEmployeesInformation() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                GetEmployeeInformation.forAllEmployees()
        );
    }

    @Then("he/she should see that the new employee has been registered")
    public void heShouldSeeThatTheNewEmployeeHasBeenRegistered() {
        EmployeeInformation lastRegisteredEmployee = OnStage.theActorInTheSpotlight().recall(Constants.LAST_REGISTERED_EMPLOYEE_INFORMATION);
        OnStage.theActorInTheSpotlight().should(
                seeThat(
                        TheEmployee.withId(lastRegisteredEmployee.getId()), CoreMatchers.equalTo(lastRegisteredEmployee)
                )
        );
    }

    @When("he/she deletes the employee information")
    public void heDeletesTheEmployeeInformation() {
        EmployeeInformation lastRegisteredEmployee = OnStage.theActorInTheSpotlight().recall(Constants.LAST_REGISTERED_EMPLOYEE_INFORMATION);
        OnStage.theActorInTheSpotlight().attemptsTo(
                DeletesEmployee.withId(lastRegisteredEmployee.getId())
        );
    }

    @Then("he/she should get a success deleted response message")
    public void heShouldGetASuccessDeletedResponseMessage() {
        OnStage.theActorInTheSpotlight().should(seeThatResponse("the delete response status code should be 200", response -> response.statusCode(200)));
        OnStage.theActorInTheSpotlight().should(seeThatResponse("the message should be **successfully! deleted Records**", response -> response.body("message", CoreMatchers.equalTo("successfully! deleted Records"))));
    }

    @And("the employee information should not exist")
    public void theEmployeeInformationShouldNotExist() {
        EmployeeInformation lastRegisteredEmployee = OnStage.theActorInTheSpotlight().recall(Constants.LAST_REGISTERED_EMPLOYEE_INFORMATION);
        OnStage.theActorInTheSpotlight().should(
                seeThat(TheEmployee.withId(lastRegisteredEmployee.getId()).fromDatabase(), CoreMatchers.nullValue())
                .because("%s should not exists")
        );
    }
}
