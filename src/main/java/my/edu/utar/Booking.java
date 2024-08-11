package my.edu.utar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class FileReaderClass
{
	// Reads user data from a file and returns a list of User objects
	public ArrayList<User> readFile(File file) 
	{
		// Check if the file exists or is null
		if (file == null || !file.exists()) 
		{
			throw new IllegalArgumentException("File does not exist or is null!");
		}

		ArrayList<User> list = new ArrayList<>();

		try (Scanner scanner = new Scanner(file)) 
		{
			// Read each line of the file
			while (scanner.hasNext()) 
			{
				// Split each line by comma to extract user data
				String[] tempList = scanner.nextLine().split(",");

				// Check if user data format is valid
				if (tempList.length != 2) 
				{
					throw new IllegalArgumentException("Invalid user data format in file!");
				}

				// Extract name and member type from the line
				String name = tempList[0];
				String memberType = tempList[1];

				// Create a new User object and add it to the list
				User user = new User(name, memberType, false,0);
				list.add(user);
			}
		} 

		catch (IOException e) 
		{
			// Handle file reading errors
			throw new IllegalArgumentException("Error reading file: " + e.getMessage());
		}

		return list;
	}
}

public class Booking 
{
	private Room room;
	private WaitingList waitingList;
	private Printer printer;


	public Booking(Room room, WaitingList waitingList, Printer printer) 
	{
		this.room = room;
		this.waitingList = waitingList;
		this.printer = printer;
	}
	// Books a room for a user
	public void setBooking(User user) 
	{
		// Validate user input
		if (user == null || 
				user.getName() == null || 
				user.getName().isEmpty() || 
				user.getMemberType() == null || 
				user.getMemberType().isEmpty())
		{
			throw new IllegalArgumentException("User details input is null or empty!!!");
		}
		
		if ((user.getMemberType().equals("VIP")&& user.getRoomBooked()>3)||
				(user.getMemberType().equals("normal")&& user.getRoomBooked()>2)||
				(user.getMemberType().equals("non")&& user.getRoomBooked()>1))
		{
			throw new IllegalArgumentException("Booking Limit Reached!!!");
		}

		String roomType = getRoomType(user);

		// Check if the room is available
		boolean roomAvailable = room.checkRoom(roomType);

		if (roomAvailable) 
		{
			// Book the room and print booking info
			room.bookRoom(roomType);
			printer.printInfo(user.getName(), user.getMemberType(), roomType);

			// Update room booking counter
			user.updateRoomBooked(+1);
		} 

		else
		{
			// Try booking the next available room type
			String nextRoomType = getNextRoomType(user);
			if (nextRoomType != null) 
			{
				// Book the next available room type and print booking info
				room.bookRoom(nextRoomType);
				printer.printInfo(user.getName(), user.getMemberType(), nextRoomType);

				// Update room booking counter
				user.updateRoomBooked(+1);
			} 

			else 
			{
				// Add user to waiting list if no room is available
				waitingList.addWaitingList(user);
				System.out.println("Room not available. Added to waiting list.");
			}
		}
	}

	// Cancel the booking of a room
	public void cancelBooking(User user) 
	{
		// Validate user input
		if (user == null || user.getName() == null || user.getMemberType() == null)
		{
			throw new IllegalArgumentException("User details input is null!!!");
		}

		// Search for the user by name
		int index = searchUser(waitingList.getWaitingList(user.getMemberType()), user.getName());

		if (index != -1) 
		{
			String roomType = getRoomType(user);

			// Cancel booking and remove user from waiting list
			room.cancelBooking(roomType);
			waitingList.removeWaitingList(user);

			System.out.println("Booking cancelled successfully.");
		} 

		else
		{
			System.out.println("User not found in the waiting list. No booking cancelled.");
		}
	}

	// Update room booking counter based on user type
	void updateRoomCounter(User user) 
	{
		
		switch (user.getMemberType()) 
		{
		case "VIP":
			room.cancelBooking("VIP");
			break;

		case "normal":
			room.cancelBooking("Deluxe");
			break;

		case "non":
			room.cancelBooking("Standard");
			break;

		default:
			// Do nothing for invalid member types
			break;
		}
	}

	// Get the room type based on user's membership type and constraints
	public String getRoomType(User user) 
	{
		// Validate user input
		if (user == null || user.getMemberType() == null) 
		{
			throw new IllegalArgumentException("User or member type is null!");
		}

		String memberType = user.getMemberType();
		int roomBooked = user.getRoomBooked();

		// Check if the user has reached the maximum number of rooms allowed to book
		if ((memberType.equals("VIP") && roomBooked>3) ||
				(memberType.equals("normal") && roomBooked>2) ||
				(memberType.equals("non-member") && roomBooked>1)) 
		{
			throw new IllegalStateException("Maximum number of rooms booked for this user type.");
		}

		String roomtype="Standard";

		switch (memberType) 
		{
		case "VIP":
			// VIP can book up to 3 rooms, prioritize VIP rooms
			if (roomBooked < 3 && room.checkRoom("VIP")) 
			{
				roomtype="VIP";
			}

			else if (room.checkRoom("Deluxe"))
			{
				roomtype= "Deluxe";
			} 

			else 
			{
				roomtype= "Standard";
			} 

			return roomtype;

		case "normal":
			// Normal user can book up to 2 rooms, exclusive reward for VIP rooms
			if (user.getReward() && room.checkRoom("VIP")) 
			{
				roomtype= "VIP";
			} 

			else if (roomBooked < 2 && room.checkRoom("Deluxe")) 
			{
				roomtype= "Deluxe";
			} 

			else
			{
				roomtype= "Standard";
			}

			return roomtype;

		case "non":
			// Non-member can book only 1 room, standard room only
			if (roomBooked < 1 && room.checkRoom("Standard"))
			{
				roomtype= "Standard";
			}

			return roomtype;

		default:
			throw new IllegalArgumentException("Invalid member type: " + memberType);

		}
	}

	// Get the next available room type based on user's membership type
	public String getNextRoomType(User user) 
	{
		String currentRoomType = getRoomType(user);
		String newRoomtype = "VIP";

		switch (currentRoomType) 
		{
		case "VIP":
			if (room.checkRoom("Deluxe"))
			{
				newRoomtype="Deluxe";
				return newRoomtype;
			}

			else if (room.checkRoom("Standard"))
			{
				newRoomtype="Standard";
				return newRoomtype;
			}
			break;

		case "Deluxe":
			if (room.checkRoom("Standard")) 
			{
				newRoomtype="Standard";
				return newRoomtype;
			}
			break;

		default:
			break;
		}

		return newRoomtype; // Return null if no next room type is available
	}

	// Add users to the waiting list
	public void addToWaitingList(ArrayList<User> userList) 
	{
		// Validate user list
		if (userList == null) 
		{
			System.out.println("User list is null! No users added to the waiting list.");
			return;
		}

		// Add each user to the waiting list
		for (User user : userList) 
		{
			// Validate user input
			if (user == null || 
					user.getName() == null || 
					user.getName().isEmpty() || 
					user.getMemberType() == null || 
					user.getMemberType().isEmpty()) 
			{
				throw new IllegalArgumentException("User details input is null or empty!!!");
			}

			waitingList.addWaitingList(user);
		}
	}

	public int searchUser(ArrayList<User> userList, String name) 
	{
		// Validate user list and name
		if (userList == null || name == null) 
		{
			throw new IllegalArgumentException("User list or name is null!");
		}

		// Search for the user by name
		for (int i = 0; i < userList.size(); i++) 
		{
			if (userList.get(i) != null && 
					userList.get(i).getName() != null && 
					userList.get(i).getName().equals(name)) 
			{
				return i;
			}
		}

		return -1;
	}

	// Main method (for testing)
	public static void main(String[] args) 
	{
		// Create room, waiting list, printer, and booking objects
		Room room = new Room();
		WaitingList waitingList = new WaitingList();
		Printer printer = new Printer();
		Booking booking = new Booking(room, waitingList, printer);

		// Initialize user list and file object
		ArrayList<User> userList = new ArrayList<>();
		File file = new File("userData.txt");

		// Check if the file exists
		if (file.exists())
		{
			// Read user data from the file
			FileReaderClass frc = new FileReaderClass();
			userList = frc.readFile(file);
			if (userList != null)
			{
				// Assign random rewards to users and add them to waiting list
				randomSetRewards(userList);
				booking.addToWaitingList(userList);

				// Set booking for each user in the list
				for (User user : userList) 
				{
					booking.setBooking(user);
				}

			}

			else 
			{
				throw new IllegalArgumentException("Failed to read user data from the file.");
			}
		} 

		else
		{
			throw new IllegalArgumentException("File not found!!");
		}
	}

	// Assign random rewards to users
	public static void randomSetRewards(ArrayList<User> userList) 
	{
		// Validate user list
		if (userList == null) 
		{
			throw new IllegalArgumentException("User list is null!");
		}

		Random random = new Random();

		for (User user : userList) 
		{
			// Validate user input
			if (user == null || user.getMemberType() == null) 
			{
				throw new IllegalArgumentException("User details input is null!!!");
			}

			// Assign reward to normal users randomly
			if (user.getMemberType().equals("normal") && random.nextInt(1000) % 7 == 0) 
			{
				user.setReward(true);
			}
		}
	}
}






