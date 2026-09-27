# Finance Manager

Command-line app to manage personal income and expenses, built to practice back-end fundamentals.

## Features

- Register transactions (income or expense) with description, amount, date and category
- List all transactions
- View total balance

## Architecture

Built with the Repository pattern, separating data access from business logic.

- `Transaction.java` — model
- `TransactionRepository.java` — interface
- `TransactionRepositoryMemory.java` — in-memory implementation
- `Main.java` — terminal menu

## Roadmap

- [x] Pure Java, in-memory storage
- [ ] MySQL persistence with JDBC
- [ ] REST API with Spring Boot
- [ ] Frontend (React or Thymeleaf)
- [ ] Automatic categorization with AI

## Tech stack

[![JAVA](https://img.shields.io/badge/java-000000?style=for-the-badge&logo=openjdk&logoColor=white)]()
