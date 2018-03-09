package com.columbus.finteck.service;


import com.columbus.finteck.contracts.TransactionSerivces;
import com.columbus.finteck.datasource.AccountRecords;
import com.columbus.finteck.models.Account;

import java.util.Objects;
import java.util.function.Supplier;

public class TransactionServiceImpl implements TransactionSerivces {

    private TransactionServiceImpl(){};

    public static final TransactionServiceImpl getServiceConnection = new TransactionServiceImpl();

    private static final String TRANSACTION_FAILED = "Transaction Failed | Transaction amount is less than account balance.";
    private static final String DEBIT_TRANSACTION_SUCCESS = "Transaction Success| %s | %s is debited from account %s";
    private static final String CREDIT_TRANSACTION_SUCCESS = "Transaction Success| %s | %s is credited to account %s";
    private static final String ACCOUNT_NOT_FOUND = "Transaction Failed | This account number does not exist.";

    // GENERATE A UNIQUE TRANSACTION ID FOR EACH TRANSACTION.
    private static final Supplier<String> TRANSACTION_ID = () -> {
        return String.valueOf(System.currentTimeMillis());
    };

    @Override
    public String DebitFromAccount(Integer fromAccountNumber, double withdrwalAmount) {
        final Account fromAccount = AccountRecords.getAccountDetails.apply(fromAccountNumber);

        if (Objects.isNull(fromAccount)) {
            return ACCOUNT_NOT_FOUND;
        }

        // VALIDATE IF THE BALANCE IS SUFFICIENT OR NOT.
        if (fromAccount.getAmount() < withdrwalAmount) {
            return TRANSACTION_FAILED;
        }

        synchronized (fromAccount) {
            // GENERATE TRANSACTION ID
            String transactionId = TRANSACTION_ID.get();

            //PLACE THE CONFIRM CALL i.e. debit action.
            if (fromAccount.debit(withdrwalAmount)) {
                AccountRecords.updateAccountDetails.accept(fromAccount.getAccountNumber(), fromAccount);
                return String.format(DEBIT_TRANSACTION_SUCCESS, transactionId, withdrwalAmount, fromAccountNumber);
            }
        }

        return TRANSACTION_FAILED;
    }

    @Override
    public String CreditToAccount(Integer toAccountNumber, double amount) {
        final Account toAccount = AccountRecords.getAccountDetails.apply(toAccountNumber);

        if (Objects.isNull(toAccount)) {
            return ACCOUNT_NOT_FOUND;
        }

        // VALIDATE IF THE BALANCE IS SUFFICIENT OR NOT.
        if (toAccount.getAmount() < amount) {
            return TRANSACTION_FAILED;
        }

        synchronized (toAccount) {
            // GENERATE TRANSACTION ID
            String transactionId = TRANSACTION_ID.get();

            //PLACE THE CONFIRM CALL i.e. debit action.
            if (toAccount.credit(amount)) {
                AccountRecords.updateAccountDetails.accept(toAccount.getAccountNumber(), toAccount);
                return String.format(CREDIT_TRANSACTION_SUCCESS, transactionId, amount, toAccountNumber);
            }
        }

        return TRANSACTION_FAILED;
    }

    @Override
    public String DebitFromAccountCreditToAccount(Integer fromAccountNumber, Integer toAccountNumber,
                                                  double withdrwalAmount) {
        final Account fromAccount = AccountRecords.getAccountDetails.apply(fromAccountNumber);
        final Account toAccount = AccountRecords.getAccountDetails.apply(toAccountNumber);

        if (Objects.isNull(fromAccount) || Objects.isNull(toAccount)) {
            return ACCOUNT_NOT_FOUND;
        }

        // VALIDATE IF THE BALANCE IS SUFFICIENT OR NOT.
        if (toAccount.getAmount() < withdrwalAmount) {
            return TRANSACTION_FAILED;
        }

        synchronized (fromAccount) {
            // GENERATE TRANSACTION ID
            String transactionId = TRANSACTION_ID.get();

            //PLACE THE CONFIRM CALL i.e. first debit and then credit, in case of error rollback.
            if (fromAccount.debit(withdrwalAmount)) {
                if (toAccount.credit(withdrwalAmount)) {
                    return String.format(CREDIT_TRANSACTION_SUCCESS, transactionId, withdrwalAmount, fromAccountNumber);
                } else {
                    // RETURN THE MONEY TO ORIGINAL ACCOUNT IF CREDIT TO DESTINATION ACCOUNT FAILED.
                    fromAccount.credit(withdrwalAmount);
                }
            }
        }

        return TRANSACTION_FAILED;
    }
}
