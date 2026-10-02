# Finance Manager

A simple command-line app to manage personal income and expenses, built with pure Java to practice back-end fundamentals.

## Features

- Add a transaction (income or expense) with a description, a value, a date and a category
- List all transactions
- Check the total balance
- Input validation: invalid values, dates and types show a message instead of crashing the app

## Architecture

The project uses the Repository pattern: the code that stores data is separate from the code that runs the app logic. For now, data is stored in memory using a list, which makes it easy to swap for a database later.

```
src/
├── model/        Transaction, TransactionType
├── repository/   TransactionRepository (interface), TransactionRepositoryMemory
├── service/      BalanceCalculator
└── ui/           Main (terminal menu)
```

## Status

Phase 1 (pure Java, in-memory storage) is complete.

- [x] `TransactionType.java`: enum with `INCOME` and `EXPENSE`
- [x] `Transaction.java`: the transaction model, with field validation
- [x] `TransactionRepository.java`: interface with the data operations
- [x] `TransactionRepositoryMemory.java`: in-memory implementation
- [x] `BalanceCalculator.java`: calculates the total balance
- [x] `Main.java`: interactive terminal menu

## Tech stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

- Java (JDK 25)
- Git with Conventional Commits

## How to run

From the project root:

```
javac -d out src/model/*.java src/repository/*.java src/service/*.java src/ui/*.java
java -cp out ui.Main
```

## Next steps

- Phase 2: save the data in a MySQL database using JDBC (not started yet)