package com.paysystem.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private int responsibleId;
    private String name;
    private List<User> employees;

    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(int responsibleId) {
        this.responsibleId = responsibleId;
    }
}
