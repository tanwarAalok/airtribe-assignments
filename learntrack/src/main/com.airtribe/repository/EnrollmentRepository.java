package repository;

import entity.Enrollment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnrollmentRepository {

    private final List<Enrollment> enrollments = new ArrayList<>();

    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }

    public Optional<Enrollment> findById(int id) {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getId() == id)
                .findFirst();
    }

    public List<Enrollment> findByStudentId(int studentId) {
        return enrollments.stream()
                .filter(e -> e.getStudentId() == studentId)
                .collect(Collectors.toList());
    }
}
