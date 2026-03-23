
import entity.Course;
import entity.Student;
import exception.EntityNotFoundException;
import service.CourseService;
import service.EnrollmentService;
import service.StudentService;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        System.out.println("Welcome to LearnTrack: Student and Course Management System");

        while (running) {
            printMenu();
            System.out.print("\nSelect an option: ");

            try{
                String input = scanner.nextLine();
                int choice = Integer.parseInt(input);

                int studentId, courseId;

                switch (choice) {
                    case 1: // Add Student
                        System.out.print("Enter First Name: ");
                        String firstName = scanner.nextLine();

                        System.out.print("Enter Last Name: ");
                        String lastName = scanner.nextLine();

                        System.out.print("Enter Email (Press Enter to skip: ");
                        String email = scanner.nextLine();

                        if(email.trim().isEmpty()){
                            studentService.addStudent(new Student(firstName, lastName));
                        } else {
                            studentService.addStudent(new Student(firstName, lastName, email));
                        }

                        break;

                    case 2: // View All Students
                        System.out.println("\n--- Registered Students ---");
                        studentService.listStudents().forEach(System.out::println);
                        break;

                    case 3: // Deactivate Student
                        System.out.print("Enter Student ID to deactivate: ");
                        studentId = Integer.parseInt(scanner.nextLine());

                        studentService.deactivateStudent(studentId);
                        System.out.println("Student deactivated.");
                        break;

                    case 4: // Add Course
                        System.out.print("Enter Course Name: ");
                        String courseName = scanner.nextLine();

                        System.out.print("Enter Description: ");
                        String description = scanner.nextLine();

                        System.out.print("Enter Duration (weeks): ");
                        int durationInWeeks = Integer.parseInt(scanner.nextLine());

                        courseService.addCourse(new Course(courseName, description, durationInWeeks));
                        break;

                    case 5: // View All Courses
                        System.out.println("\n--- Available Courses ---");
                        courseService.listCourses().forEach(System.out::println);
                        break;

                    case 6: // Deactivate Course
                        System.out.print("Enter Course ID to deactivate: ");
                        courseId = Integer.parseInt(scanner.nextLine());

                        courseService.deactivateCourse(courseId);
                        System.out.println("Course deactivated.");
                        break;

                    case 7: // Enroll Student
                        System.out.print("Enter Student ID: ");
                        studentId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Enter Course ID: ");
                        courseId = Integer.parseInt(scanner.nextLine());

                        enrollmentService.enrollStudent(studentId, courseId);
                        break;

                    case 0: // Exit
                        running = false;
                        System.out.println("Exiting system... Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric ID or option.");
            } catch (EntityNotFoundException e) {
                System.out.println("Business Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--------------------------------");
        System.out.println("     MAIN MANAGEMENT MENU");
        System.out.println("--------------------------------");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Deactivate Student");
        System.out.println("4. Add Course");
        System.out.println("5. View All Courses");
        System.out.println("6. Deactivate Course");
        System.out.println("7. Enroll Course");
        System.out.println("0. Exit");
    }
}
