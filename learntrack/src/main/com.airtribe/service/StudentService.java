package service;

import entity.Student;
import exception.EntityNotFoundException;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final StudentRepository studentRepository = new StudentRepository();

    public List<Student> listStudents() {
        List<Student> allStudents = studentRepository.findAll();
        List<Student> activeStudents = new ArrayList<>();
        for (Student allStudent : allStudents) {
            if (allStudent.isActive()) {
                activeStudents.add(allStudent);
            }
        }
        return activeStudents;
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public Student getStudentById(int id) throws EntityNotFoundException {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student s = getStudentById(id);
        s.setActive(false);
    }
}
