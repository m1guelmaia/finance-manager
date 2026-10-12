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

                System.out.println("===== FINANCE MANAGER =====\n");
                System.out.println("1 - Add transaction");
                System.out.println("2 - List transactions");
                System.out.println("3 - Show balance");
                System.out.println("4 - Exit\n");
                System.out.println("===========================\n");
                System.out.print("Choose an option: ");
                option = sc.nextLine();
                if (option.isBlank()) {
                    System.out.println("Invalid option.\n");
                    continue;
                }

                option = option.substring(0, 1);

                switch (option) {

                    case "1": {
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
                            String typeInput = sc.nextLine();
                            TransactionType type;
                            if (typeInput.equals("1")) {
                                type = TransactionType.INCOME;
                            } else if (typeInput.equals("2")) {
                                type = TransactionType.EXPENSE;
                            } else {
                                throw new IllegalArgumentException("Invalid type. Use 1 for income or 2 for expense");
                            }

                            Transaction transaction = repository.create(description, value, date, category, type);

                            System.out.println("\nTransaction has been created.");
                            System.out.println(transaction + "\n");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid value. Use numbers with a period, like 50.25.\n");
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date. Use dd/MM/yyyy.\n");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage() + "\n");
                        }
                        break;
                    }

                    case "2": {
                        List<Transaction> transactions = repository.findAll();
                        if (transactions.isEmpty()) {
                            System.out.println("No transactions found.\n");
                        } else {
                            for (Transaction transaction : transactions) {
                                System.out.println(transaction + "\n");
                            }
                        }
                        break;
                    }

                    case "3": {
                        BigDecimal balance = balanceCalculator.calculate(repository.findAll());
                        System.out.println("Balance: R$ " + String.format("%.2f", balance) + "\n");
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
}
