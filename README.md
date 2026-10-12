# Finance Manager

A simple command-line app to manage personal income and expenses, built with Java, MySQL and JDBC to practice back-end fundamentals.

## Features

- Add a transaction (income or expense) with a description, a value, a date and a category
- List all transactions
- Check the total balance
- Save transactions in a MySQL database
- Input validation: invalid values, dates and types show a message instead of crashing the app

## Architecture

The project uses the Repository pattern: the code that stores data is separate from the code that runs the app logic.

In phase 1, data was stored in memory using a list. In phase 2, the app uses MySQL with JDBC.

```
src/
├── db/           ConnectionFactory
├── model/        Transaction, TransactionType
├── repository/   TransactionRepository, TransactionRepositoryMemory, TransactionRepositoryJdbc
├── service/      BalanceCalculator
└── ui/           Main

sql/
└── schema.sql
```

## Status

Phase 1 (pure Java, in-memory storage) is complete.

Phase 2 (MySQL with JDBC) is complete.

- [x] `TransactionType.java`: enum with `INCOME` and `EXPENSE`
- [x] `Transaction.java`: the transaction model, with field validation
- [x] `TransactionRepository.java`: interface with the data operations
- [x] `TransactionRepositoryMemory.java`: in-memory implementation
- [x] `BalanceCalculator.java`: calculates the total balance
- [x] `ConnectionFactory.java`: creates the database connection
- [x] `TransactionRepositoryJdbc.java`: JDBC implementation with MySQL
- [x] `Main.java`: interactive terminal menu using the JDBC repository

## Tech stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

- Java (JDK 25)
- MySQL
- JDBC
- Git with Conventional Commits

## Database setup

Create the database:

```sql
CREATE DATABASE finance_manager;
```

Run the schema:

```bash
mysql -u root -p finance_manager < sql/schema.sql
```

Create a database user for the app:

```sql
CREATE USER 'finance_app'@'localhost' IDENTIFIED BY 'your_password_here';
GRANT SELECT, INSERT, UPDATE, DELETE ON finance_manager.* TO 'finance_app'@'localhost';
```

Create a `db.properties` file in the project root:

```properties
db.url=jdbc:mysql://localhost:3306/finance_manager
db.user=finance_app
db.password=your_password_here
```

The real `db.properties` file is ignored by Git. Use `db.properties.example` as a template.

## MySQL driver

This project uses MySQL Connector/J.

Download the driver and place the `.jar` file inside the `lib/` folder:

```text
lib/mysql-connector-j-26.7.0.jar
```

The `lib/*.jar` files are ignored by Git.

## How to run

From the project root, compile the project:

```bash
javac -cp "lib/mysql-connector-j-26.7.0.jar" -d out src/db/*.java src/model/*.java src/repository/*.java src/service/*.java src/ui/*.java
```

Run the app:

```bash
java -cp "out;lib/mysql-connector-j-26.7.0.jar" ui.Main
```

On macOS or Linux, use `:` instead of `;` in the classpath.

## Next steps

- Improve error handling
- Add update and delete operations
- Add reports by month and category
- Build a REST API with Spring Boot
- Study PostgreSQL in a future phase
