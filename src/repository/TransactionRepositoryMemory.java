package repository;

import model.Transaction;
import model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryMemory implements TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    private long nextId = 1;

    @Override
    public Transaction create(String description, BigDecimal value, LocalDate date, String category, TransactionType type) {
        Transaction transaction = new Transaction(nextId, description, value, date, category, type);
        transactions.add(transaction);
        nextId++;
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        return List.copyOf(transactions);
    }
}
