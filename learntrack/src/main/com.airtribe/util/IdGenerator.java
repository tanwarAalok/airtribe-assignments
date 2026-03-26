package util;

public class IdGenerator {
    private static int studentIdCounter = 1;
    private static int courseIdCounter = 1;
    private static int enrollmentIdCounter = 1;
    private static int instructorIdCounter = 1;

    public static int getNextStudentId() {
        return studentIdCounter++;
    }

    public static int getNextCourseId() {
        return courseIdCounter++;
    }

    public static int generateEnrollmentId() {
        return enrollmentIdCounter++;
    }

    public static int generateInstructorId() {
        return instructorIdCounter++;
    }
}
