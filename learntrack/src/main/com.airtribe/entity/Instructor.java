package entity;

import util.IdGenerator;

public class Instructor extends Person {

    public Instructor(String firstName, String lastName, String email) {
        super(IdGenerator.generateInstructorId(), firstName, lastName, email);
    }
}
