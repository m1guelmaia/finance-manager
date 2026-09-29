# Finance Manager

This is a simple command-line app to manage personal income and expenses. I am building it to practice back-end fundamentals.

With this app, you can add a transaction. A transaction can be income or an expense. Each one has a description, an amount, a date, and a category. You can also see a list of all your transactions and check your total balance.

The project uses the Repository pattern. This means the code that saves data is separate from the code that runs the app logic. Right now, the data is stored in memory, using a list.

- `Transaction.java` — the model of a transaction
- `TransactionRepository.java` — the interface with the data operations
- `TransactionRepositoryMemory.java` — saves the transactions in memory
- `Main.java` — the terminal menu

This is only the first phase of the project. Next, I want to save the data in a MySQL database, using JDBC. After that, I want to build a REST API with Spring Boot. Later, I want to add a frontend and automatic categorization with AI.

## Tech stack

[![JAVA](https://img.shields.io/badge/java-000000?style=for-the-badge&logo=openjdk&logoColor=white)]()
