# LearnTrack: Student and Course Management System

LearnTrack is a console-based Java application designed to manage students, courses, and enrollments. It demonstrates core Object-Oriented Programming (OOP) principles including Inheritance, Encapsulation, and Polymorphism, alongside a layered architecture (Entity-Repository-Service).

## 🚀 Features
- **Student Management**: Add students (with optional email), view active students, and deactivate accounts.
- **Course Management**: Create courses, list available courses, and manage course status.
- **Enrollment System**: Link students to courses and automatically assign student batches based on enrollment.
- **Robust Input**: Handles invalid numeric inputs and business logic exceptions (e.g., searching for non-existent IDs).

## 📊 Class Relationships
The following diagram illustrates how the components interact:


- **Inheritance**: `Student` extends `Person`.
- **Composition**: `Enrollment` contains `studentId` and `courseId`.
- **Layered Flow**: `Main` (UI) -> `Service` -> `Repository` -> `Entity`.

## 🛠️ How to Compile and Run
1. **Ensure JDK 21** is installed.
2. **Navigate** to the `learntrack` directory.
3. **Run the `Main.java` file.**: