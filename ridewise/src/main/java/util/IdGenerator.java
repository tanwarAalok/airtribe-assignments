package util;

public class IdGenerator {

    private static int riderIdCount = 0;
    private static int driverIdCount = 0;
    private static int rideIdCount = 0;

    public static int generateRiderId(){
        riderIdCount++;
        return riderIdCount;
    }

    public static int generateDriverId(){
        driverIdCount++;
        return driverIdCount;
    }

    public static int generateRideId(){
        rideIdCount++;
        return rideIdCount;
    }
}
