package com.columbus.finteck.contracts;

public interface TransactionSerivces {

    public String DebitFromAccount(Integer fromAccountNumber, double withdrwalAmount);
    public String CreditToAccount(Integer fromAccountNumber, double amount);
    public String DebitFromAccountCreditToAccount(Integer fromAccountNumber, Integer toAccount, double withdrwalAmount);
}
