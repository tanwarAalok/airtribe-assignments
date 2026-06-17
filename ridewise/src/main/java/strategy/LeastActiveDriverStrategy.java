package strategy;

import model.Driver;
import model.Rider;

import java.util.List;

public class LeastActiveDriverStrategy implements RideMatchingStrategy{

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        Driver leastActive = null;
        int minRides = Integer.MAX_VALUE;

        for (Driver driver : drivers) {
            if (driver.isAvailable()) {
                if (driver.getCompletedRidesCount() < minRides) {
                    minRides = driver.getCompletedRidesCount();
                    leastActive = driver;
                }
            }
        }
        return leastActive;
    }
}
