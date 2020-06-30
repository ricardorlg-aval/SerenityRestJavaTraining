package com.labs.digital.aval.testing.training.models;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class EmployeeInformation {
    @JsonAlias("employee_name")
    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id="";

    @JsonAlias("employee_salary")
    @JsonProperty("salary")
    private String salary;

    @JsonAlias("employee_age")
    @JsonProperty("age")
    private String age;

    @JsonProperty("profile_image")
    private String profileImage="";

    public EmployeeInformation() {
    }

    public EmployeeInformation(String name, String age, String salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSalary() {
        return salary;
    }

    public String getAge() {
        return age;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeInformation)) return false;
        EmployeeInformation that = (EmployeeInformation) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(id, that.id) &&
                Objects.equals(salary, that.salary) &&
                Objects.equals(age, that.age) &&
                Objects.equals(profileImage, that.profileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, salary, age, profileImage);
    }

    @Override
    public String toString() {
        return "EmployeeInformation{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", salary='" + salary + '\'' +
                ", age='" + age + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}
