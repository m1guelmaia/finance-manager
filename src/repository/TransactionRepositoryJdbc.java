package repository;

import model.Transaction;
import model.TransactionType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryJdbc implements TransactionRepository {

    private final Connection connection;

    public TransactionRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Transaction create(String description, BigDecimal value, LocalDate date, String category, TransactionType type) {
        String sql = "INSERT INTO transactions (description, value, transaction_date, category, transaction_type) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, description);
            preparedStatement.setBigDecimal(2, value);
            preparedStatement.setDate(3, Date.valueOf(date));
            preparedStatement.setString(4, category);
            preparedStatement.setString(5, type.name());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    return new Transaction(id, description, value, date, category, type);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating transaction", e);
        }
        throw new RuntimeException("Error creating transaction: no ID returned");
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT id, description, value, transaction_date, category, transaction_type FROM transactions";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    long id = resultSet.getLong("id");
                    String description = resultSet.getString("description");
                    BigDecimal value = resultSet.getBigDecimal("value");
                    LocalDate date = resultSet.getDate("transaction_date").toLocalDate();
                    String category = resultSet.getString("category");
                    TransactionType transactionType = TransactionType.valueOf(resultSet.getString("transaction_type"));

                    Transaction transaction = new Transaction(id, description, value, date, category, transactionType);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Error finding transactions", e);
        }
        return transactions;
    }
}
