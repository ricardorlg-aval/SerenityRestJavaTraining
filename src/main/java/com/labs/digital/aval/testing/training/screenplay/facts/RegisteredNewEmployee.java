package com.labs.digital.aval.testing.training.screenplay.facts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import com.labs.digital.aval.testing.training.exceptions.CompromisedException;
import com.labs.digital.aval.testing.training.models.EmployeeCreationResponse;
import com.labs.digital.aval.testing.training.models.EmployeeInformation;
import com.labs.digital.aval.testing.training.screenplay.tasks.RegistersANewEmployee;
import com.labs.digital.aval.testing.training.utils.Constants;
import com.labs.digital.aval.testing.training.utils.JacksonHelper;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.facts.Fact;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;

/*Los facts son hechos acerca de los actores, podemos decir que son precondiciones
que deben cumplirse, con esto garantizamos que si no cumplen con ciertas condiciones,
no ejecutemos las demas etapas, no se instrumentaliza por que esto no hace parte del reporte.
 */
public class RegisteredNewEmployee implements Fact {
    @Override
    public void setup(Actor actor) {
        Faker faker = new Faker();
        String age = String.valueOf(faker.number().numberBetween(18, 45));
        String salary = String.valueOf(faker.number().numberBetween(2000000, 3000000));
        EmployeeInformation employeeInformation = new EmployeeInformation(faker.name().fullName(), age, salary);
        try {
            Serenity.recordReportData().withTitle("Employee information to register")
                    .andContents(JacksonHelper.getInstance().getObjectMapper().writeValueAsString(employeeInformation));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        actor.wasAbleTo(
                RegistersANewEmployee.withInformation(employeeInformation)
        );
        actor.should(
                ResponseConsequence.seeThatResponse("registered response code should be 200", response -> response.statusCode(200))
        );
        EmployeeInformation registeredInformation = actor.asksFor(LastResponse.received()).as(EmployeeCreationResponse.class).getData();
        if (registeredInformation.getId().isEmpty()) {
            throw new CompromisedException("The employee id is empty");
        }
        actor.remember(Constants.LAST_REGISTERED_EMPLOYEE_INFORMATION, registeredInformation);
    }

    public static RegisteredNewEmployee withRandomData() {
        return new RegisteredNewEmployee();
    }

    //implementamos este metodo para que en el reporte veamos una explicacion clara del Fact del actor
    @Override
    public String toString() {
        return "Registered a new employee with random data";
    }
}
