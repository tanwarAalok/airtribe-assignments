package model;

import util.IdGenerator;

public class Ride {
    private int id;
    private Rider rider;
    private Driver driver;
    private double distance;
    private RideStatus status;

    public Ride(Rider rider, double distance) {
        this.id = IdGenerator.generateRideId();
        this.rider = rider;
        this.distance = distance;
        this.status = RideStatus.REQUESTED;
    }

    public int getId() { return id; }
    public Rider getRider() { return rider; }
    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
    public double getDistance() { return distance; }
    public RideStatus getStatus() { return status; }
    public void setStatus(RideStatus status) { this.status = status; }
}
