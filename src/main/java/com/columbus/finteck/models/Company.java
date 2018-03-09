package com.columbus.finteck.models;

import java.util.List;

public class Company {

    private String companyName;
    private List<Department> departments;

    public Company(String companyName, List<Department> departments) {
        this.companyName = companyName;
        this.departments = departments;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
