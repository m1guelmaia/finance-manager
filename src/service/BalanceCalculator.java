package service;

import model.Transaction;
import model.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public class BalanceCalculator {

    public BigDecimal calculate(List<Transaction> transactions) {
        BigDecimal balance = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.INCOME) {
                balance = balance.add(transaction.getValue());
            } else if (transaction.getType() == TransactionType.EXPENSE) {
                balance = balance.subtract(transaction.getValue());
            }
        }
        return balance;
    }
}
