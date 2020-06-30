package com.labs.digital.aval.testing.training.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AllEmployeeResponse {
    @JsonProperty("data")
    private List<EmployeeInformation> data;
    @JsonProperty("status")
    private String status;

    public List<EmployeeInformation> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
