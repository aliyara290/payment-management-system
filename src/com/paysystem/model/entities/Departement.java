package com.paysystem.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Departement {

    private String responsibleId;
    private String name;
    private List<User> employees;

    public Departement(List<User> employees, String name, String responsibleId) {
        this.employees = new ArrayList<>();;
        this.name = name;
        this.responsibleId = responsibleId;
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

    public String getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(String responsibleId) {
        this.responsibleId = responsibleId;
    }
}
