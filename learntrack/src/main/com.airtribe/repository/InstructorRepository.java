package repository;

import entity.Instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstructorRepository {

    private List<Instructor> instructors = new ArrayList<>();

    public void save(Instructor instructor){
        instructors.add(instructor);
    }

    public List<Instructor> findAll(){
        return instructors;
    }

    public Optional<Instructor> findById(int id){
        return instructors.stream().filter(instructor -> instructor.getId() == id).findFirst();
    }
}
