# Library Management System (Java)

A professional-grade Library Management System built in Java that demonstrates clean code practices, Object-Oriented Programming (OOP), and SOLID principles.

## 🏗️ System Architecture

The system is designed with a "Layered Architecture" approach, separating the data models from the business logic and the creation logic.

### Class Diagram
The following diagram illustrates the relationships between core components:



### Design Patterns Used
1.  **Factory Pattern (`LibraryFactory`)**: Encapsulates object creation logic. This allows us to add mandatory fields or validation in the future without breaking the code that calls it.
2.  **Observer Pattern (`Observer/Patron`)**: Patrons "subscribe" to a book's availability. When a book is returned, the system automatically notifies the next patron in the reservation queue.
3.  **Strategy Pattern (`RecommendationStrategy`)**: Decouples the recommendation algorithm from the user class. You can swap between `AuthorPreferenceStrategy` or a `NewReleaseStrategy` at runtime.

## 🚀 Features
- **Book Management**: Full CRUD operations with search by ISBN, Title, or Author.
- **Lending Process**: Handled by a dedicated service to ensure the Single Responsibility Principle.
- **Reservation System**: Queue-based system for high-demand books.
- **Recommendation Engine**: Analyzes borrowing history to suggest relevant reads.
- **Logging**: Integrated logging for all checkouts, returns, and system errors.

## 📁 Project Structure
```text
src/main/java/com/library/
├── entity/           # Data entities (Book, Patron, BookStatus)
├── service/         # Business logic (LibraryManager, LendingService)
├── patterns/        # Design Pattern implementations
│   ├── factory/     # Object creation logic
│   ├── observer/    # Notification interfaces
│   └── strategy/    # Pluggable algorithms
└── Main.java        # Entry point