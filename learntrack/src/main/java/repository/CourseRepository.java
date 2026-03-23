package repository;

import entity.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {

    private List<Course> courses = new ArrayList<>();

    public void save(Course course) {
        courses.add(course);
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    public Optional<Course> findById(int id) {
        return courses.stream()
                .filter(course -> course.getId() == id)
                .findFirst();
    }
}
