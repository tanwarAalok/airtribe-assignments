# RideWise

RideWise is a simple Maven-based Java console application for managing a ride-sharing workflow in memory. It supports registering riders and drivers, finding an available driver for a ride request, completing rides, and generating a fare receipt.

## Features

- Register riders with a name and location
- Register drivers with a name and location
- View currently available drivers
- Request a ride for a rider
- Complete a ride and generate a fare receipt
- View the ride audit log stored in memory


## Project Structure

- `src/main/java/Main.java` - console entry point
- `src/main/java/model/` - domain objects such as `Rider`, `Driver`, `Ride`, and `FareReceipt`
- `src/main/java/service/` - in-memory business logic for riders, drivers, and rides
- `src/main/java/strategy/` - ride-matching and fare-calculation strategies
- `src/main/java/exception/` - custom runtime exceptions
- `src/main/java/util/` - ID generation helper

## How It Works

RideWise keeps all data in memory while the program is running.

- Riders and drivers are stored in lists inside their respective services
- `NearestDriverStrategy` matches the closest available driver to a rider
- `DefaultFareStrategy` calculates the ride fare when a ride is completed
- IDs for riders, drivers, and rides are generated sequentially using `IdGenerator`

Because the app is in-memory only, all data is reset when you exit the program.


## Console Menu

When the app starts, you can choose from the following options:

1. Add Rider
2. Add Driver
3. View Available Drivers
4. Request Ride
5. Complete Ride
6. View Rides Log System
7. Exit

### Typical Flow

1. Add one or more riders.
2. Add one or more drivers.
3. Request a ride for a rider by ID and enter the trip distance.
4. Complete the ride using the ride ID to generate a fare receipt.
5. View the ride log to inspect ride status and assignments.


