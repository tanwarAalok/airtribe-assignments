package service;

import model.*;
import strategy.FareStrategy;
import strategy.RideMatchingStrategy;

import java.util.ArrayList;
import java.util.List;

public class RideService {
    private List<Ride> rideList = new ArrayList<>();
    private final DriverService driverService;
    private final RideMatchingStrategy matchingStrategy;
    private final FareStrategy fareStrategy;

    public RideService(DriverService driverService, RideMatchingStrategy matchingStrategy, FareStrategy  fareStrategy) {
        this.driverService = driverService;
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(Rider rider, double distance){
        Ride ride = new Ride(rider, distance);

        List<Driver> availableDrivers = driverService.getAvailableDrivers();
        Driver assignedDriver = matchingStrategy.findDriver(rider, availableDrivers);

        assignedDriver.setAvailable(false);
        ride.setDriver(assignedDriver);
        ride.setStatus(RideStatus.ASSIGNED);

        rideList.add(ride);

        return ride;
    }

    public FareReceipt completeRide(int rideId){
        Ride ride = getRideById(rideId);

        ride.setStatus(RideStatus.COMPLETED);

        Driver driver = ride.getDriver();
        driver.setAvailable(true);
        driver.incrementCompletedRides();

        double finalFare = fareStrategy.calculateFare(ride);
        return new FareReceipt(rideId, finalFare);
    }

    public Ride getRideById(int rideId){
        for (Ride ride : rideList){
            if (ride.getId() == rideId){
                return ride;
            }
        }
        return null;
    }

    public List<Ride> getAllRides(){
        return rideList;
    }
}
