Feature: Manage employees information

  Scenario: Create a new Employee information
    Given Martina wants to register a new Employee information
    When she makes a post with the employee information
    Then the employee should be registered

  Scenario: Get employee Information
    Given Martin has registered a new Employee information
    When he ask for all the registered employees information
    Then he should see that the new employee has been registered

  Scenario: Delete Employee Information
    Given Martin has registered a new Employee information
    When he deletes the employee information
    Then he should get a success deleted response message
    And the employee information should not exist
