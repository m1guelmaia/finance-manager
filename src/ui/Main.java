package ui;

import db.ConnectionFactory;
import model.Transaction;
import model.TransactionType;
import repository.TransactionRepository;
import repository.TransactionRepositoryJdbc;
import service.BalanceCalculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT);

        try (Connection connection = ConnectionFactory.getConnection()) {

            TransactionRepository repository = new TransactionRepositoryJdbc(connection);
            BalanceCalculator balanceCalculator = new BalanceCalculator();

            String option = "";
            while (!option.equals("4")) {

                showMenu();
                option = readOption(sc);
                if (option.isBlank()) {
                    System.out.println("Invalid option.\n");
                    continue;
                }

                switch (option) {

                    case "1": {
                        addTransaction(sc, dtf, repository);
                        break;
                    }

                    case "2": {
                        listTransactions(repository);
                        break;
                    }

                    case "3": {
                        showBalance(repository, balanceCalculator);
                        break;
                    }

                    case "4": {
                        System.out.println("Thanks for using Finance Manager.");
                        System.out.println("Exiting...");
                        break;
                    }

                    default: {
                        System.out.println("Invalid option.\n");
                    }
                }
            }
            sc.close();
        } catch (SQLException | IOException e) {
            System.out.println("Could not connect to the database. Please check if MySQL is running and db.properties is correct.");
        }
    }

    private static void showMenu() {
        System.out.println("===== FINANCE MANAGER =====\n");
        System.out.println("1 - Add transaction");
        System.out.println("2 - List transactions");
        System.out.println("3 - Show balance");
        System.out.println("4 - Exit\n");
        System.out.println("===========================\n");
        System.out.print("Choose an option: ");
    }

    private static String readOption(Scanner sc) {
        String option = sc.nextLine();
        if (option.isBlank()) {
            return "";
        }

        return option.substring(0, 1);
    }

    private static TransactionType readTransactionType(Scanner sc) {
        String type = sc.nextLine();
        return switch (type) {
            case "1" -> TransactionType.INCOME;
            case "2" -> TransactionType.EXPENSE;
            default -> throw new IllegalArgumentException("Invalid type. Use 1 for income or 2 for expense.");
        };
    }

    private static void addTransaction(Scanner sc, DateTimeFormatter dtf, TransactionRepository repository) {
        try {
            System.out.print("Description: ");
            String description = sc.nextLine();
            System.out.print("Value: ");
            BigDecimal value = new BigDecimal(sc.nextLine());
            System.out.print("Date (use dd/MM/yyyy): ");
            LocalDate date = LocalDate.parse(sc.nextLine(), dtf);
            System.out.print("Category: ");
            String category = sc.nextLine();
            System.out.print("Type (1 - Income, 2 - Expense): ");
            TransactionType type = readTransactionType(sc);

            Transaction transaction = repository.create(description, value, date, category, type);

            System.out.println("\nTransaction has been created.");
            System.out.println(transaction + "\n");
        } catch (NumberFormatException e) {
            System.out.println("Invalid value. Use numbers with a period, like 50.25.\n");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Use dd/MM/yyyy.\n");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (RuntimeException e) {
            System.out.println("Could not create transaction. Please try again.\n");
        }
    }

    private static void listTransactions(TransactionRepository repository) {
        try {
            List<Transaction> transactions = repository.findAll();
            if (transactions.isEmpty()) {
                System.out.println("No transactions found.\n");
            } else {
                for (Transaction transaction : transactions) {
                    System.out.println(transaction + "\n");
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Could not load transactions. Please try again.\n");
        }
    }

    private static void showBalance(TransactionRepository repository, BalanceCalculator balanceCalculator) {
        try {
            BigDecimal balance = balanceCalculator.calculate(repository.findAll());
            System.out.println("Balance: R$ " + String.format("%.2f", balance) + "\n");
        } catch (RuntimeException e) {
            System.out.println("Could not calculate balance. Please try again.\n");
        }
    }
}
