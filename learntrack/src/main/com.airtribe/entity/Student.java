package entity;


import util.IdGenerator;

public class Student extends Person{

    private String batch;

    public Student(String firstName, String lastName, String email) {
        super(IdGenerator.getNextStudentId(), firstName, lastName, email);
    }

    public Student(String firstName, String lastName) {
        this(firstName, lastName, "");
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-20s  | Email: %-20s| Batch: %-10s",
                getId(),
                getFirstName() + " " + getLastName(),
                getEmail(),
                getBatch()
        );
    }
}
