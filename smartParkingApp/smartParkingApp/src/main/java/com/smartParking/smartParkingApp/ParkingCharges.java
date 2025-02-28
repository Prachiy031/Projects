package com.smartParking.smartParkingApp;

public class ParkingCharges {
    private static final double RatePerHour = 50.0;
    
    public static double calculateCharges(long durationInSeconds) {
    	double hours = durationInSeconds/ (60*60);
    	return hours*RatePerHour;
    }
}
