package com.labs.digital.aval.testing.training.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeCreationResponse {

    @JsonProperty("data")
    private EmployeeInformation data;

    @JsonProperty("status")
    private String status;

    public EmployeeInformation getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
