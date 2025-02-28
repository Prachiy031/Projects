package com.smartParking.smartParkingApp;

public class ParkingRecord {
    private int slotId;
    private String vehicleNumber;
    private long startTime; // Store as long for UNIX timestamp
    private long endTime;   // Store as long for UNIX timestamp

    // Constructor
    public ParkingRecord(int slotId, String vehicleNumber, long startTime, long endTime) {
        this.slotId = slotId;
        this.vehicleNumber = vehicleNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public int getSlotId() {
        return slotId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    // Optionally, you can add a toString() method for easy debugging
    @Override
    public String toString() {
        return "ParkingRecord{" +
                "slotId=" + slotId +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
