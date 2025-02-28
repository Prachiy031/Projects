
package com.smartParking.smartParkingApp;

import java.sql.Timestamp;

public class ParkingSlot {
    // Slot data
    private int slotId;       
    private boolean isOccupied;
    private String vehicleNumber;
    private Timestamp startTime;      // Start time of parking in the given slot
    private Timestamp endTime;        // End time of parking in the given slot
    private double totalCharge;       // Total charge for parking

    public ParkingSlot(int slotId) {       // All attributes initialized
        super();
        this.slotId = slotId;
        this.isOccupied = false;
        this.vehicleNumber = "";
        this.startTime = null;
        this.endTime = null;
        this.totalCharge = 0.0; // Initialize total charge
    }

    // Getter methods
    public int getSlotId() {
        return slotId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public double getTotalCharge() {
        return totalCharge; // Return total charge
    }
    
    public void occupySlot(String vehicleNumber) {   // For occupying slot
        if (this.isOccupied) {
            System.out.println("Sorry, this slot is currently occupied. Try another slot.");
        } else {
            this.vehicleNumber = vehicleNumber;
            this.isOccupied = true;
            this.startTime = new Timestamp(System.currentTimeMillis());  // Time in milliseconds
            //System.out.println("Slot " + slotId + " occupied by vehicle " + vehicleNumber + ".");
        }
    }
    
    public void freeSlot() {        // For emptying slot
        if (this.isOccupied) {
            this.endTime = new Timestamp(System.currentTimeMillis());
            long duration = getParkingDuration(); // Get the parking duration
            this.totalCharge = calculateCharge(duration); // Calculate the total charge
            this.vehicleNumber = "";
            this.isOccupied = false;
            System.out.println("\nSlot " + slotId + " is now free. Total charge: " + totalCharge);
        } else {
            System.out.println("\nSlot is already free.");
        }
    }
    
    public long getParkingDuration() {
        if (endTime == null || startTime == null) {
            return 0;
        }
        return (endTime.getTime() - startTime.getTime()) / 1000; // Duration in seconds
    }

    private double calculateCharge(long durationInSeconds) {
        double ratePerSecond = 10;
        return durationInSeconds * ratePerSecond; // Calculate total charge based on duration
    }

}

