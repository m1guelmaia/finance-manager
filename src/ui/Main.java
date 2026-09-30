package ui;

import model.Transaction;
import model.TransactionType;
import repository.TransactionRepository;
import repository.TransactionRepositoryMemory;
import service.BalanceCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US);

        TransactionRepository repository = new TransactionRepositoryMemory();
        BalanceCalculator balanceCalculator = new BalanceCalculator();

        char choice = ' ';
        while (choice != '4') {

            System.out.println("===== FINANCE MANAGER =====\n");
            System.out.println("1 - Add transaction");
            System.out.println("2 - List transactions");
            System.out.println("3 - Show balance");
            System.out.println("4 - Exit\n");
            System.out.println("===========================\n");
            System.out.print("Choose an option: ");
            choice = sc.next().charAt(0);
            sc.nextLine();

            switch (choice) {

                case '1': {
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
                            throw new IllegalArgumentException("Invalid type");
                        }

                        Transaction transaction = repository.create(description, value, date, category, type);

                        System.out.println("\nTransaction has been created.");
                        System.out.println(transaction.toString() + "\n");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid value. Use numbers with a period, like 50.25.\n");
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date. Use dd/MM/yyyy.\n");
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid type. Use 1 for income or 2 for expense");
                    }
                    break;
                }

                case '2': {
                    List<Transaction> transactions = repository.findAll();
                    if (transactions.isEmpty()) {
                        System.out.println("No transactions found.\n");
                    } else {
                        for (Transaction transaction : transactions) {
                            System.out.println(transaction.toString() + "\n");
                        }
                    }
                    break;
                }

                case '3': {
                    BigDecimal balance = balanceCalculator.calculate(repository.findAll());
                    System.out.println("Balance: " + String.format("%.2f", balance) + "\n");
                    break;
                }

                case '4': {
                    System.out.println("Thanks for using our program.");
                    System.out.println("Exiting...");
                    break;
                }

                default: {
                    System.out.println("Invalid choice.\n");
                }
            }
        }

        sc.close();
    }
}
