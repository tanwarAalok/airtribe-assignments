package service;

import entity.Instructor;
import exception.EntityNotFoundException;
import repository.InstructorRepository;

import java.util.ArrayList;
import java.util.List;

public class InstructorService {

    private final InstructorRepository instructorRepository = new InstructorRepository();

    public void addInstructor(Instructor instructor){
        instructorRepository.save(instructor);
    }

    public List<Instructor> listInstructors(){
        List<Instructor> instructors = instructorRepository.findAll();
        List<Instructor> activeInstructors = new ArrayList<>();
        for(Instructor instructor : instructors){
            if(instructor.isActive()){
                activeInstructors.add(instructor);
            }
        }
        return activeInstructors;
    }

    public Instructor getInstructorById(int id) throws EntityNotFoundException{
        return instructorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));
    }
}
