package entity;

import util.IdGenerator;

import java.util.Date;

public class Enrollment {

    private int id;
    private int studentId;
    private int courseId;
    private Date enrollmentDate;
    private EnrollmentStatus status;

    public Enrollment(int studentId, int courseId) {
        this.id = IdGenerator.generateEnrollmentId();
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = new Date();
        this.status = EnrollmentStatus.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Enrollment ID: %-5d | Student ID: %-5d | Course ID: %-5d | Date: %s | Status: %-10s",
                id, studentId, courseId, enrollmentDate, status);
    }
}
