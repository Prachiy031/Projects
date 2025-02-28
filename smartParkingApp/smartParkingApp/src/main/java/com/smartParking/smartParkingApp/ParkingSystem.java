package com.smartParking.smartParkingApp;

import java.sql.Timestamp;
import java.util.Scanner;

public class ParkingSystem {
	private ParkingLots parkingLot;
	private ParkingDAO parkingDAO;
	
	public ParkingSystem(int totalSlots) {
		this.parkingLot = new ParkingLots(totalSlots);
		this.parkingDAO = new ParkingDAO();
	}
	
	public void start() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("\n******************************");
			System.out.println("Please enter desired option: ");
			System.out.println("1. Park a vehicle");
			System.out.println("2. Free a slot");
			System.out.println("3. Show All slots");
			System.out.println("4. Show All parking records");
			System.out.println("5. Exit");
			
			int choice = scanner.nextInt();
			
			switch(choice) {
			case 1:
				System.out.println("\nEnter vehicle number : ");
				String vehicleNumber = scanner.next();
				
				System.out.println("\nEnter slot number to park from 1 to 10.");
                int desiredSlotNumber = scanner.nextInt();
                
                // Check if the desired slot is valid and available
                if (desiredSlotNumber < 1 || desiredSlotNumber > 10) {
                    System.out.println("\nInvalid slot number. Please enter a number between 1 and 10.");
                } else {
                    ParkingSlot desiredSlot = parkingLot.getSlots().get(desiredSlotNumber - 1);
                    if (!desiredSlot.isOccupied()) {
                    	boolean isBooked = parkingDAO.bookSlot(desiredSlotNumber, vehicleNumber);
          
                    	if(isBooked) {
//                    		desiredSlot.occupySlot(vehicleNumber);  // Occupy the slot
//                    		Timestamp startTime = new Timestamp(System.currentTimeMillis()); // Current time in milliseconds
//                            parkingDAO.saveParkingRecord(desiredSlotNumber, vehicleNumber, startTime, null); // End time is set to 0

                            System.out.println("\nVehicle parked in slot " + desiredSlotNumber + ".");
                        }else {
                        	System.out.println("\nFailed to book the slot");
                    	}
                    }else {
                    	System.out.println("\nSlot " + desiredSlotNumber + " is already occupied. Please choose another slot.");
                    }
                }
                break;
				
			case 2:
				System.out.println("\nEnter slot number to free :");
				int slotId = scanner.nextInt();
//				ParkingSlot slot  = parkingLot.getSlots().get(slotId-1);
//				System.out.println("\n***** " + slot.getVehicleNumber());
				long duration = parkingDAO.releaseSlot(slotId, 1, 2);
				
//				double charges = ParkingCharges.calculateCharges(duration);

//				parkingDAO.saveParkingRecord(slotId, slot.getVehicleNumber(),slot.getStartTime(),slot.getEndTime());
				break;
				
				
				
			case 3:
				parkingLot.showAllSlots();
				break;
			
			case 4:
				parkingDAO.showAllParkingRecords();
                break;
             
			case 5:
                System.out.println("\nExited");
                scanner.close();
                System.exit(0);
             
			default:
                System.out.println("\nInvalid option. Try again.");
                
			}
			
		}
	}
	
	public static void main(String[] args) {
        ParkingSystem system = new ParkingSystem(10); // 10 parking slots
        system.start();
    }
}
