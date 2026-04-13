package service;

import entity.Course;
import entity.Instructor;
import entity.Student;
import exception.EntityNotFoundException;
import repository.CourseRepository;
import repository.InstructorRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseService {

    private final CourseRepository courseRepository = new  CourseRepository();
    private final InstructorService instructorService;

    public CourseService(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> listCourses(){
        List<Course> allCourses = courseRepository.findAll();
        List<Course> activeCourses = new ArrayList<>();
        for (Course course : allCourses) {
            if (course.isActive()) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }

    public void deactivateCourse(int id) throws EntityNotFoundException {
        Course s = getCourseById(id);
        s.setActive(false);
    }

    public Course getCourseById(int id) throws EntityNotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    public void assignInstructorToCourse(int courseId, int instructorId) throws EntityNotFoundException {
        Course course = getCourseById(courseId);
        Instructor instructor = instructorService.getInstructorById(instructorId);

        course.setInstructorName(instructor.getFirstName() + " " + instructor.getLastName());
    }
}
