package my.edu.utar;

import java.util.HashMap;
import java.util.Map;

public class Room 
{
	private Map <String, Integer> availableRooms;

	public Room() 
	{
		availableRooms = new HashMap <> ();
		availableRooms.put("VIP", 5); // Initialize with 5 VIP rooms
		availableRooms.put("Deluxe", 10); // Initialize with 10 Deluxe rooms
		availableRooms.put("Standard", 20); // Initialize with 20 Standard rooms
	}

	public synchronized boolean checkRoom(String roomType) 
	{
		return availableRooms.getOrDefault(roomType, 0) > 0;
	}

	public synchronized void bookRoom(String roomType) 
	{
		int count = availableRooms.getOrDefault(roomType, 0);
		availableRooms.put(roomType, count - 1);
	}

	public synchronized void cancelBooking(String roomType) 
	{
		int count = availableRooms.getOrDefault(roomType, 0);
		availableRooms.put(roomType, count + 1);
	}
}