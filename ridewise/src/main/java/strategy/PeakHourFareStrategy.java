package strategy;

import model.Ride;

public class PeakHourFareStrategy implements FareStrategy{
    private static final double BASE_FARE = 8.0;
    private static final double PER_KM_RATE = 3.5;

    @Override
    public double calculateFare(Ride ride) {
        return BASE_FARE + (ride.getDistance() * PER_KM_RATE);
    }
}
