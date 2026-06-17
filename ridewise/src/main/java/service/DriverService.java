package service;

import exception.DriverNotFoundException;
import model.Driver;

import java.util.ArrayList;
import java.util.List;

public class DriverService {
    List<Driver> driverList = new ArrayList<>();

    public Driver getDriverById(int id) throws DriverNotFoundException {
        for(Driver driver : driverList){
            if(driver.getId() == id){
                return driver;
            }
        }

        throw new DriverNotFoundException("Driver with id " + id + " not found.");
    }

    public Driver registerDriver(String name, double location){
        Driver driver = new Driver(name,  location);
        driverList.add(driver);
        return driver;
    }

    public Driver updateAvailability(int driverId, boolean available){
        Driver driver = getDriverById(driverId);
        driver.setAvailable(available);
        return driver;
    }

    public List<Driver> getAvailableDrivers(){
        List<Driver> availableDrivers = new ArrayList<>();
        for(Driver driver : driverList){
            if(driver.isAvailable()){
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }
}
