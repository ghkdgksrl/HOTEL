package my.edu.utar;

import java.util.*;

public class WaitingList 
{
	private ArrayList <User> VIP;  // Array to store VIP member in waiting list
	private ArrayList <User> normal;  // Array to store normal member in waiting list
	private ArrayList <User> non;  // Array to store non-member in waiting list

	public WaitingList() 
	{
		this.VIP = new ArrayList <User> ();
		this.normal = new ArrayList <User> ();
		this.non = new ArrayList <User> ();
	}

	// Method to add a user to the appropriate waiting list based on member type
	public void addWaitingList(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        String memberType = user.getMemberType();
        if (memberType == null) {
            throw new IllegalArgumentException("Invalid member type.");
        }

        switch (memberType) {
            case "VIP":
                VIP.add(user);
                break;
            case "normal":
                normal.add(user);
                break;
            case "non-member":
                non.add(user);
                break;
            default:
                throw new IllegalArgumentException("Invalid member type: " + memberType);
        }
    }

	// Method to remove a user from a waiting list (list not specified)
	public void removeWaitingList(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        String memberType = user.getMemberType();
        if (memberType == null) {
            throw new IllegalArgumentException("Invalid member type.");
        }

        switch (memberType) {
            case "VIP":
                VIP.remove(user);
                break;
            case "normal":
                normal.remove(user);
                break;
            case "non-member":
                non.remove(user);
                break;
            default:
                throw new IllegalArgumentException("Invalid member type: " + memberType);
        }
    }

	// Method to get users from a specific waiting list (list not specified)
	public ArrayList<User> getWaitingList(String memberType) {
        if (memberType == null) {
            throw new IllegalArgumentException("Invalid member type.");
        }

        switch (memberType) {
            case "VIP":
                return VIP;
            case "normal":
                return normal;
            case "non-member":
                return non;
            default:
                throw new IllegalArgumentException("Invalid member type: " + memberType);
        }
    }
}
