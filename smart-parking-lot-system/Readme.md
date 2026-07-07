# Smart Parking Lot - Low-Level Design (LLD)

## 1. Objective
Design the low-level architecture for a backend system of a smart parking lot handling vehicle entry and exit management, parking space allocation, and fee calculation.

## 2. Core Entities and Enums

### Enums
```java
public enum VehicleType {
    MOTORCYCLE, CAR, BUS
}

public enum SpotType {
    SMALL, COMPACT, LARGE
}

public enum TicketStatus {
    ACTIVE, PAID, LOST
}
```

## 3. Data Model (Database Schema)

We will use a relational database (e.g., PostgreSQL or MySQL) to maintain ACID properties, which is crucial for handling concurrency and transactions involving payments and spot reservations.

**Table: `Vehicles`**
*   `vehicle_id` (PK, UUID)
*   `license_plate` (VARCHAR, Unique)
*   `vehicle_type` (ENUM)

**Table: `Parking_Spots`**
*   `spot_id` (PK, UUID)
*   `floor_number` (INT)
*   `spot_type` (ENUM: SMALL, COMPACT, LARGE)
*   `is_available` (BOOLEAN, Default: TRUE)
*   `version` (INT) - *Used for Optimistic Concurrency Control*

**Table: `Parking_Tickets`**
*   `ticket_id` (PK, UUID)
*   `vehicle_id` (FK -> Vehicles)
*   `spot_id` (FK -> Parking_Spots)
*   `entry_time` (TIMESTAMP)
*   `exit_time` (TIMESTAMP, Nullable)
*   `fee_amount` (DECIMAL, Nullable)
*   `status` (ENUM: ACTIVE, PAID)

---

## 4. Class Design (Core Services)

### 4.1. Spot Allocation Algorithm
To efficiently assign parking spots to incoming vehicles, we need a fast lookup mechanism (O(1) ideally).

**Strategy:** Use In-Memory Data Structures (like Redis or Concurrent Hash Maps) backed by the database. We can maintain a `Queue` or a `Min-Heap` of available spots for each `SpotType`.

**Mapping Rules:**
*   `VehicleType.MOTORCYCLE` -> `SpotType.SMALL` (Can also fit in COMPACT or LARGE if needed, but optimally SMALL).
*   `VehicleType.CAR` -> `SpotType.COMPACT` (Can also fit in LARGE).
*   `VehicleType.BUS` -> `SpotType.LARGE`.

**Allocation Logic:**
```java
public class ParkingLotManager {
    // In-memory queues for fast allocation
    private Map<SpotType, Queue<ParkingSpot>> availableSpots;

    public synchronized ParkingSpot findSpot(VehicleType type) {
        SpotType requiredSpotType = mapVehicleToSpotType(type);
        Queue<ParkingSpot> spots = availableSpots.get(requiredSpotType);
        
        if (spots != null && !spots.isEmpty()) {
            return spots.poll(); // O(1) extraction of an available spot
        }
        // Fallback: Check if upgrade is possible (e.g., CAR parking in LARGE spot)
        return null; // Lot is full for this vehicle type
    }
}
```

### 4.2. Entry and Exit Management (Check-In & Check-Out)

**Check-In Flow:**
1. Camera/Sensor detects `license_plate` and `vehicle_type`.
2. System calls `findSpot(vehicleType)`.
3. If a spot is returned, create a `Parking_Ticket` with current timestamp.
4. Update `Parking_Spots.is_available = FALSE` in the DB.
5. Issue the ticket to the user.

**Check-Out Flow:**
1. User provides ticket at the exit.
2. System retrieves `Parking_Ticket` and calculates duration (`exit_time - entry_time`).
3. Call `FeeCalculator`.
4. Process payment.
5. Update `Parking_Spots.is_available = TRUE`.
6. Add the spot back to the in-memory `availableSpots` queue.

### 4.3. Fee Calculation Logic
Implement a Strategy Pattern for fee calculation, allowing easy updates to pricing rules.

```java
public class FeeCalculator {
    private static final double MOTORCYCLE_RATE = 2.0; // $2 per hour
    private static final double CAR_RATE = 4.0;        // $4 per hour
    private static final double BUS_RATE = 8.0;        // $8 per hour

    public double calculateFee(ParkingTicket ticket, VehicleType type) {
        long durationInMillis = System.currentTimeMillis() - ticket.getEntryTime().getTime();
        double hours = Math.ceil(durationInMillis / (1000.0 * 60 * 60)); // Round up to nearest hour
        
        switch (type) {
            case MOTORCYCLE: return hours * MOTORCYCLE_RATE;
            case CAR: return hours * CAR_RATE;
            case BUS: return hours * BUS_RATE;
            default: throw new IllegalArgumentException("Unknown Vehicle Type");
        }
    }
}
```

---

## 5. Concurrency Handling

Handling multiple vehicles entering at the exact same time (race condition for the same spot).

**Solution 1: Database Optimistic Locking (OCC)**
Add a `version` column to the `Parking_Spots` table.
```sql
UPDATE Parking_Spots 
SET is_available = FALSE, version = version + 1 
WHERE spot_id = ? AND is_available = TRUE AND version = ?;
```
If the update count is `0`, it means another thread just took this spot. The application will catch this and retry the spot allocation with the next available spot.

**Solution 2: Distributed Locks (Redis)**
If running multiple instances of the backend, use Redis to handle spot queues and acquire a distributed lock (e.g., Redisson) when assigning a spot. Since Redis is single-threaded, `LPOP` operations on a list of available spots for a specific `SpotType` are inherently atomic and thread-safe.

## 6. Real-Time Availability Update

*   **Pub/Sub Mechanism**: Use Redis Pub/Sub, Kafka, or WebSockets to stream parking availability changes to digital display boards at the entrance and to a centralized dashboard.
*   Whenever a spot is allocated or freed, an event (`SpotAllocatedEvent` or `SpotFreedEvent`) is published.