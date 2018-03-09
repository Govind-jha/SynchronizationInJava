package com.columbus.finteck.models;

public class Employee {

    private String name;
    private int id;
    Account account;

    public Employee(String name, int id, Account account) {
        this.name = name;
        this.id = id;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
