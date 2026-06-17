package model;

import java.time.LocalDateTime;

public class FareReceipt {
    private final int rideId;
    private final double amount;
    private final LocalDateTime generatedAt;

    public FareReceipt(int rideId, double amount) {
        this.rideId = rideId;
        this.amount = amount;
        this.generatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("Receipt [Ride ID: %d | Fare: Rs%.2f | Generated At: %s]",
                rideId, amount, generatedAt.toLocalTime());
    }
}
