import model.Driver;
import model.FareReceipt;
import model.Ride;
import model.Rider;
import service.DriverService;
import service.RideService;
import service.RiderService;
import strategy.DefaultFareStrategy;
import strategy.FareStrategy;
import strategy.NearestDriverStrategy;
import strategy.RideMatchingStrategy;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();

        RideMatchingStrategy matchingStrategy = new NearestDriverStrategy();
        FareStrategy fareStrategy = new DefaultFareStrategy();

        RideService rideService = new RideService(driverService, matchingStrategy, fareStrategy);

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Ride Sharing Management System Console Engine ===");

        while (true) {
            try {
                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1. Add Rider");
                System.out.println("2. Add Driver");
                System.out.println("3. View Available Drivers");
                System.out.println("4. Request Ride");
                System.out.println("5. Complete Ride");
                System.out.println("6. View Rides Log System");
                System.out.println("7. Exit");
                System.out.print("Select an alternative option: ");

                String input = scanner.nextLine().trim();
                switch (input) {
                    case "1":  // Add rider
                        System.out.print("Enter Rider Name: ");
                        String rName = scanner.nextLine().trim();
                        System.out.print("Enter Location coordinate (Numeric): ");
                        double rLoc = Double.parseDouble(scanner.nextLine());
                        riderService.registerRider(rName, rLoc);
                        System.out.println("Rider registered successfully.");
                        break;

                    case "2": // Add driver
                        System.out.print("Enter Driver Name: ");
                        String dName = scanner.nextLine().trim();
                        System.out.print("Enter Current Location coordinate (Numeric): ");
                        double dLoc = Double.parseDouble(scanner.nextLine());
                        driverService.registerDriver(dName, dLoc);
                        System.out.println("Driver registered successfully.");
                        break;

                    case "3": // View Available Drivers
                        List<Driver> available = driverService.getAvailableDrivers();
                        if (available.isEmpty()) {
                            System.out.println("No drivers available currently.");
                        } else {
                            System.out.println("Available Drivers:");
                            for (Driver d : available) {
                                System.out.printf("- ID: %d | Name: %s | Location: %.1f\n", d.getId(), d.getName(), d.getCurrentLocation());
                            }
                        }
                        break;

                    case "4": // Request ride
                        System.out.print("Enter Rider ID: ");
                        int riderId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Trip Distance (KM): ");
                        double distance = Double.parseDouble(scanner.nextLine());

                        Rider rider = riderService.getRiderById(riderId);
                        if (rider == null) {
                            System.out.println("Error: Rider profile not found.");
                            break;
                        }

                        Ride ride = rideService.requestRide(rider, distance);
                        System.out.printf("Ride assigned successfully! Matched with Driver: %s\n", ride.getDriver().getName());
                        break;

                    case "5": // Complete Ride
                        System.out.print("Enter Ride ID to complete: ");
                        int cRideId = Integer.parseInt(scanner.nextLine());
                        FareReceipt receipt = rideService.completeRide(cRideId);
                        System.out.println("Ride completed successfully!");
                        System.out.println(receipt);
                        break;

                    case "6": //View Rides Log System
                        List<Ride> rides = rideService.getAllRides();
                        if (rides.isEmpty()) {
                            System.out.println("No rides logged in system memory.");
                        } else {
                            System.out.println("Global Rides Audit Logs:");
                            for (Ride r : rides) {
                                String driverName = r.getDriver() != null ? r.getDriver().getName() : "UNASSIGNED";
                                System.out.printf("- Ride ID: %d | Rider: %s | Driver: %s | Status: %s\n",
                                        r.getId(), r.getRider().getName(), driverName, r.getStatus());
                            }
                        }
                        break;

                    case "7":
                        System.out.println("Exiting System Ecosystem Engine. Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid structural numeric index value choice selected.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input format mismatch error parsing value parameters: Please input numerical metrics.");
            } catch (Exception e) {
                System.out.println("System execution warning error boundary captured: " + e.getMessage());
            }
        }
    }
}
