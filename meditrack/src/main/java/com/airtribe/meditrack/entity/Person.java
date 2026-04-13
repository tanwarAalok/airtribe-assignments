package com.airtribe.meditrack.entity;

public class Person {
    private final String id;
    private final String name;
    private final int age;
    private final String gender;

    public Person(String id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public int getAge() { return age; }

    public String getGender() { return gender; }
}
