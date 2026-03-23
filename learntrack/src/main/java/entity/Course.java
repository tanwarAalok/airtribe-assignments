package entity;

import util.IdGenerator;

public class Course {

    private final int id;
    private String courseName;
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

    @Override
    public String toString() {
        return String.format("ID: %d | Course Name: %-15s | Description: %-35s | Duration: %d weeks",
                getId(),
                getCourseName(),
                getDescription(),
                getDurationInWeeks()
        );
    }
}
