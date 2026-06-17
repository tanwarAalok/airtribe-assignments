package model;

import util.IdGenerator;

public class Driver {
    private int id;
    private String name;
    private double currentLocation;
    private boolean available;
    private int completedRidesCount;

    public Driver(String name, double currentLocation){
        this.id = IdGenerator.generateDriverId();
        this.name = name;
        this.currentLocation = currentLocation;
        this.available = true;
        this.completedRidesCount = 0;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(double currentLocation) { this.currentLocation = currentLocation; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public int getCompletedRidesCount() { return completedRidesCount; }
    public void incrementCompletedRides() { this.completedRidesCount++; }
}
