package entity;


import util.IdGenerator;

public class Student extends Person{

    private String batch;
    private boolean active;

    public Student(String firstName, String lastName, String email) {
        super(IdGenerator.getNextStudentId(), firstName, lastName, email);
        this.active = true;
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

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
