package service;

import entity.Course;
import entity.Enrollment;
import entity.Instructor;
import entity.Student;
import exception.EntityNotFoundException;
import repository.EnrollmentRepository;

public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository =  new EnrollmentRepository();
    private final CourseService courseService;
    private final StudentService studentService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public void enrollStudent(int studentId, int courseId) throws EntityNotFoundException {
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);

        Enrollment newEnrollment = new Enrollment(studentId, courseId);
        enrollmentRepository.save(newEnrollment);

        student.setBatch(course.getCourseName());
    }
}
