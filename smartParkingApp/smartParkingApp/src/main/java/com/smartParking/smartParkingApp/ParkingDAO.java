package com.smartParking.smartParkingApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
//import java.time.Instant;
import java.util.List;

//this is data access object used to handle CRUD operations in database
public class ParkingDAO {
	
	//database connection object
	private Connection connection;
	
	
	public ParkingDAO() {
		try {
			//establish connection to database using DatabaseConnectionClass
			connection = DatabaseConnection.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//check available parking slots
	public int getAvailableSlots() {
		int availableSlots = 0;
		String query = "SELECT COUNT(*) FROM parking_slots WHERE is_occupied = false";
		try(PreparedStatement statement = connection.prepareStatement(query)){ //execute query safely without SQL injection
			 ResultSet resultSet = statement.executeQuery(); //executed query and retrieves the result as ResultSet
			 if(resultSet.next()) { //cursor goes to next row
				 availableSlots = resultSet.getInt(1);  //retrieves the value of 1st column
			 }
		}catch(SQLException e){
			e.printStackTrace();
		}
		return availableSlots;
	}
	
	
	//book parking slots
	
	public boolean bookSlot(int slotId, String vehicleNumber) {
		
	    // Check if the slot is available first
	    if (!isSlotAvailable(slotId)) {
	        System.out.println("Slot " + slotId + " is already occupied.");
	        return false; // Slot is not available
	    }

	    Timestamp startTime = new Timestamp(System.currentTimeMillis()); // Get current time in milliseconds

	    String query = "UPDATE parking_slots SET is_occupied = true, total_charge = 0, vehicle_number = ? WHERE slot_id = ? AND is_occupied = false";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	    	
	        statement.setString(1, vehicleNumber);
	        statement.setInt(2, slotId);
	        
	        int rowsAffected = statement.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            // Save the parking record with start time
	            saveParkingRecord(slotId, vehicleNumber, startTime, null); // 0 as a placeholder for endTime
	            return true; // Slot booked successfully
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Booking failed
	}


	// Method to check if a slot is available
	public boolean isSlotAvailable(int slotId) {
	    String query = "SELECT is_occupied FROM parking_slots WHERE slot_id = ?";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	    	
	        statement.setInt(1, slotId);
	        ResultSet resultSet = statement.executeQuery();
	        
	        if (resultSet.next()) {
	            return !resultSet.getBoolean("is_occupied");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If the slot does not exist or another error occurs
	}

/*
	// Method to check if a slot is available
	public boolean getSlotsById(int slotId) {
		    String query = "SELECT * FROM parking_slots WHERE slot_id = ?";
		    try (PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setInt(1, slotId);
		        ResultSet resultSet = statement.executeQuery();
		        
		        if (resultSet.next()) {
		        	parkingSlot.add(resultSet);
		            return !resultSet.getBoolean("is_occupied");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return false; // If the slot does not exist or another error occurs
	}
*/
	
	//release parking slot and calculate parking time
	public long releaseSlot(int slotId, long startTime, long endTime) {
		
		double charge = 0.0;
		long totalDurationInSec = 0;
		// Prepare the SQL statement to update the slot
	    String query0 = "SELECT ((end_time - start_time)/1000) total_time from parking_records WHERE slot_id = ?";
	    try (PreparedStatement statement = connection.prepareStatement(query0)) {
	    	
	        statement.setInt(1, slotId); // Set the slot ID to update

	        // Execute the update
	        ResultSet rows = statement.executeQuery();
	        
	        if (rows.next()) {
	        	totalDurationInSec = rows.getLong("total_time");
	        	charge = ParkingCharges.calculateCharges(totalDurationInSec); // Calculate charges	
	        	// Log the relevant details for debugging
	        	System.out.println("Releasing Slot " + slotId + " Duration = " + totalDurationInSec + " seconds, Charges = " + charge);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print the exception for debugging
	        return 0; // Update failed
	    }

	    // Prepare the SQL statement to update the slot
	    String query = "UPDATE parking_slots SET is_occupied = false, vehicle_number = null, total_charge = ? WHERE slot_id = ?";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setDouble(1, charge); // Set calculated charge
	        statement.setInt(2, slotId); // Set the slot ID to update

	        // Execute the update
	        int rowsAffected = statement.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("Slot " + slotId + " released successfully.");
	            return totalDurationInSec; // Update successful
	        } else {
	            System.out.println("No rows affected. Slot " + slotId + " may not be occupied.");
	            return 0; // No rows updated, check if slot is occupied
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print the exception for debugging
	        return 0; // Update failed
	    }
	}

	
	public static String formatCharges(double charges) {
	    DecimalFormat df = new DecimalFormat("#.00"); // Format to 2 decimal places
	    return df.format(charges);
	}

	public void saveParkingRecord(int slotId, String vehicleNumber, Timestamp startTime, Timestamp endTime) {
		
	    String query = "INSERT INTO parking_records(slot_id, vehicle_number, start_time, end_time) VALUES (?, ?, ?, ?)";
	    
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	    	
	        statement.setInt(1, slotId);
	        statement.setString(2, vehicleNumber);
	        
	        // Set startTime and endTime as they are already Timestamp objects
	        statement.setTimestamp(3, startTime);
	        statement.setTimestamp(4, endTime); // Use null if the vehicle is still parked and endTime is null
	        
	        int rowsAffected = statement.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("Parking record has been saved successfully");
	        } else {
	            System.out.println("Failed to save parking records.");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    //show all parking records
	
	public void showAllParkingRecords() {
	    String query = "SELECT * FROM parking_records";
	    
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	    	
	        System.out.println("Slot id | Vehicle Number | Start time | End Time");
	        ResultSet resultSet = statement.executeQuery();
	        
	        while (resultSet.next()) {
	        	
	            int slotId = resultSet.getInt("slot_id");
	            String vehicleNumber = resultSet.getString("vehicle_number");
	            Timestamp startTime = resultSet.getTimestamp("start_time");
	            Timestamp endTime = resultSet.getTimestamp("end_time");

	            String formattedEndTime = (endTime != null) ? endTime.toString() : "Still Parked";
	            System.out.println(slotId + "  | " + vehicleNumber + "      | " + startTime + "        | " + formattedEndTime);
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
