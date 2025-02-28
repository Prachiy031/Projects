package com.smartParking.smartParkingApp;

import java.util.ArrayList;
import java.util.List;

public class ParkingLots{
	private List<ParkingSlot> slots;       //collection of all ParkingSlot objects in the parking lot
	
	public ParkingLots(int totalSlots) {
		slots = new ArrayList<>();
		for(int i=1;i<=totalSlots;i++) {
			slots.add(new ParkingSlot(i));
		}
	}
	
	public void occupySlot(int slotId, String vehicleNumber) {  //to occupy any slot
		ParkingSlot slot = slots.get(slotId-1);
		slot.occupySlot(vehicleNumber);
		System.out.println("\nSlot: "+slotId+" is occupied by vehicle: "+vehicleNumber);
	}
	
	public void freeSlot(int slotId) {
		ParkingSlot slot = slots.get(slotId-1);
		slot.freeSlot();
		System.out.println("\nSlot: "+slotId+" is free now.");
	}
	
	public void showAllSlots() {
		for(ParkingSlot slot: slots) {
			if(slot.isOccupied()==true)
			System.out.println("\nSlot : "+slot.getSlotId()+(slot.isOccupied()?" Occupied by "+slot.getVehicleNumber()+" for duration "+slot.getParkingDuration()+"." :" is not available"));
		}
	}
	
	public List<ParkingSlot> getSlots() {
        return this.slots;
    }
	
	
	
	
}
