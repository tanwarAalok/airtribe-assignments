package model;

import util.IdGenerator;

public class Rider {
    private int id;
    private String name;
    private double location; // Taking simplified 1D coordinate for location

    public Rider(String name, double location){
        this.id = IdGenerator.generateRiderId();
        this.name = name;
        this.location = location;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getLocation() { return location; }
    public void setLocation(double location) { this.location = location; }
}
