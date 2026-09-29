package repository;

import model.Transaction;
import model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {

    Transaction create(String description, BigDecimal value, LocalDate date, String category, TransactionType type);

    List<Transaction> findAll();
}
