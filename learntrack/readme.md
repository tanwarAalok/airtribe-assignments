# LearnTrack: Student, Course, and Instructor Management System

LearnTrack is a console-based Java application designed to manage students, courses, instructors, and enrollments. It demonstrates core Object-Oriented Programming (OOP) principles including Inheritance, Encapsulation, and Polymorphism, alongside a layered architecture (`Entity -> Repository -> Service`).

## Features
- **Student Management**: Add students (with optional email), view active students, and deactivate accounts.
- **Course Management**: Create courses, list active courses, and deactivate courses.
- **Instructor Management**: Add instructors, view active instructors, and assign an instructor to a course.
- **Enrollment System**: Link students to courses and automatically assign student batches based on enrollment.
- **Input and Error Handling**: Handles invalid numeric inputs and business logic exceptions (for example, non-existent entity IDs).

## Console Menu
Current menu options in `Main`:

1. Add Student  
2. View All Students  
3. Deactivate Student  
4. Add Course  
5. View All Courses  
6. Deactivate Course  
7. Enroll Course  
8. Add Instructor  
9. View All Instructors  
10. Assign instructor to course  
0. Exit

## Class Relationships
- **Inheritance**: `Student` and `Instructor` extend `Person`.
- **Composition**: `Enrollment` stores `studentId` and `courseId`.
- **Course-Instructor Link**: `CourseService.assignInstructorToCourse(...)` resolves an instructor by ID and stores instructor display name on `Course`.
- **Layered Flow**: `Main` (UI) -> `Service` -> `Repository` -> `Entity`.

## How to Compile and Run
1. Ensure Java 21 is installed.
2. Open a terminal in the `learntrack` directory.
3. Compile the project:

```powershell
mvn clean compile
```

4. Run the application:

```powershell
java -cp target/classes Main
```

## Quick Instructor Flow
Use this sequence to test the new instructor module quickly:
1. Add Course (Option 4)
2. Add Instructor (Option 8)
3. Assign instructor to course (Option 10)
4. View All Courses (Option 5) to confirm assigned instructor
5. View All Instructors (Option 9)
