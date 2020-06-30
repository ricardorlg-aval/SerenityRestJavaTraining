package com.labs.digital.aval.testing.training.endPoints;

public enum DummyApiEndPoints {
    CREATE_EMPLOYEE_END_POINT("/create"),
    GET_ALL_EMPLOYEE("/employees"),
    DELETE_EMPLOYEE_ENDPOINT("/delete/{employee_id}");
    private final String route;

    DummyApiEndPoints(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
