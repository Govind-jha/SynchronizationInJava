package com.columbus.finteck.models;

import com.columbus.finteck.contracts.ExecuteTransactions;

public class Account  implements ExecuteTransactions {

    private final Integer accountNumber;
    private final String accountType;
    private Double amount;

    public Account(Integer accountNumber, String accountType, double amount) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.amount = amount;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public synchronized Boolean credit(double amount) {
        this.amount += amount;
        return true;
    }

    public synchronized Boolean debit(double amount) {
        if(this.amount < amount) return false;

        this.amount -= amount;
        return true;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountType='" + accountType + '\'' +
                ", amount=" + amount +
                '}';
    }
}
