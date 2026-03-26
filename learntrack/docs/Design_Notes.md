### 1. Why ArrayList instead of Array?
We used `java.util.ArrayList` in our Repositories because:
- **Dynamic Resizing**: Unlike standard arrays, `ArrayList` grows automatically as we add more students or courses.
- **Ease of Use**: It provides built-in methods like `removeIf`, `stream()`, and `add()`, which reduce boilerplate code.
- **Flexibility**: It simplifies the "Deactivate" and "Search" logic through the use of Streams and Predicates.

### 2. Static Members Usage
Static members were used in the `util.IdGenerator` class:
- **Fields**: `studentIdCounter`, `courseIdCounter`, and `enrollmentIdCounter` are static so their values persist across all instances of the classes.
- **Why**: This ensures that every time a new Student or Course is created, they receive a unique, incremental ID regardless of which part of the code initializes them.

### 3. Inheritance Usage
Inheritance is implemented with `Student` extending `Person`:
- **What was gained**: 
  - **Code Reusability**: Common fields like `firstName`, `lastName`, and `email` are defined once in `Person` and inherited by `Student`.
  - **Organization**: It allows us to keep the `Student` class focused strictly on academic data (batch, active status) while the `Person` class handles general identity.