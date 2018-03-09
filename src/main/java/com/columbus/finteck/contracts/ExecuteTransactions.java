package com.columbus.finteck.contracts;

public interface ExecuteTransactions {
    /**
     * Returns true if amount is credited successfully.
     *
     * @param amount
     * @return Boolean
     */
    public Boolean credit(double amount);

    /**
     * Returns true if amount is debited successfully otherwise false.
     *
     * @param amount
     * @return Boolean
     */
    public Boolean debit(double amount);
}
