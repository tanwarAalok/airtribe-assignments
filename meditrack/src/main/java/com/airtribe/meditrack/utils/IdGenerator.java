package com.airtribe.meditrack.utils;

public class IdGenerator {

    public static int patientIdCounter = 0;
    public static int doctorIdCounter = 0;

    public static int getNextPatientId() {
        return patientIdCounter++;
    }
    public static int getNextDoctorId() {
        return doctorIdCounter++;
    }
}
