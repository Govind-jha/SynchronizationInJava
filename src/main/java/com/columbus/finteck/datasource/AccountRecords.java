package com.columbus.finteck.datasource;

import com.columbus.finteck.models.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class AccountRecords {

    private AccountRecords() {   /* Singleton Class */ }

    ;

    /**
     * Populate Account data in the data source
     */
    private static final Map<Integer, Account> accountDetails = new HashMap<>();

    static {
        accountDetails.put(101, new Account(101, "", 50));
        accountDetails.put(102, new Account(102, "", 100));
        accountDetails.put(103, new Account(103, "", 100));
        accountDetails.put(104, new Account(104, "", 100));
    }

    ;

    /**
     * Return the Account details for given accountNumber
     */
    public static final Function<Integer, Account> getAccountDetails = accountNumber -> accountDetails.get(accountNumber);

    /**
     * Update account details in the Collection
     */
    public static final BiConsumer<Integer, Account> updateAccountDetails = (accountNumber, accountObject) ->
            accountDetails.put(accountNumber, accountObject);
}
