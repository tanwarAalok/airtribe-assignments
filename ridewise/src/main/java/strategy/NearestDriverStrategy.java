package strategy;

import model.Driver;
import model.Rider;

import java.util.List;

public class NearestDriverStrategy implements RideMatchingStrategy{

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers){
        Driver nearestDriver = null;
        double minDistance = Double.MAX_VALUE;

        for (Driver driver : drivers){
            if(driver.isAvailable()){
                double distance = Math.abs(driver.getCurrentLocation() - rider.getLocation());
                if(distance < minDistance){
                    nearestDriver = driver;
                    minDistance = distance;
                }
            }
        }

        return nearestDriver;
    }
}
