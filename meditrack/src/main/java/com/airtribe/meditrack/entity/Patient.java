package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.utils.IdGenerator;

public class Patient extends Person{

    public Patient(String name, int age, String gender){
        super("Patient#" + IdGenerator.getNextPatientId(), name, age, gender);
    }
}
