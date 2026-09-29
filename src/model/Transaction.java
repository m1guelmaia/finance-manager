package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Transaction {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final long id;
    private String description;
    private BigDecimal value;
    private LocalDate date;
    private String category;
    private final TransactionType type;

    public Transaction(long id, String description, BigDecimal value, LocalDate date, String category, TransactionType type) {

        validateDescription(description);
        validateValue(value);
        validateDate(date);
        validateCategory(category);

        this.id = id;
        this.description = description;
        this.value = value;
        this.date = date;
        this.category = category;
        this.type = Objects.requireNonNull(type, "Type cannot be null");
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public TransactionType getType() {
        return type;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    public void setValue(BigDecimal value) {
        validateValue(value);
        this.value = value;
    }

    public void setDate(LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    public void setCategory(String category) {
        validateCategory(category);
        this.category = category;
    }

    private static void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
    }

    private static void validateValue(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Value must be greater than zero");
        }
    }

    private static void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }

    private static void validateCategory(String category) {
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%d] %s | %s | %s | %s | R$ %.2f", id, FORMATTER.format(date), type, category, description, value);
    }
}
