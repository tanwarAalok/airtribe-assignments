package entity;

import util.IdGenerator;

public class Course {

    private final int id;
    private String courseName;
    private String instructorName;
    private String description;
    private int durationInWeeks;
    private boolean active;

    public Course(String courseName, String description, int durationInWeeks) {
        this.id = IdGenerator.getNextCourseId();
        this.courseName = courseName;
        this.description = description;
        this.durationInWeeks = durationInWeeks;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationInWeeks() {
        return durationInWeeks;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Course Name: %-15s | Description: %-35s | Duration: %d weeks | Instructor: %-15s",
                getId(),
                getCourseName(),
                getDescription(),
                getDurationInWeeks(),
                getInstructorName()
        );
    }
}
