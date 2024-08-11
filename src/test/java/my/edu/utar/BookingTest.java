package my.edu.utar;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class BookingTest 
{
	private Room room;
	private WaitingList waitingList;
	private Printer printer;
	private Booking booking;    

	@Before
	public void setUp() 
	{
		room = mock(Room.class);
		waitingList = mock(WaitingList.class);
		printer = mock(Printer.class);
		booking = new Booking(room, waitingList, printer);

	}

	@Test
	@Parameters(method = "bookingTestData")
	public void testSetBooking(
			User user, 
			boolean roomAvailable, 
			boolean addToWaitingList, 
			String expectedRoomType,
			boolean canbook) 
	{
		when(room.checkRoom(anyString())).thenReturn(roomAvailable);

		booking.setBooking(user);

		if (user.getReward()==true && user.getMemberType().equals("normal")) 
		{
			expectedRoomType = "VIP";
			canbook=true;
			if (!roomAvailable) 
			{

				canbook=false;
			} 
			
			else if (user.getMemberType().equals("VIP") && expectedRoomType.equals("VIP")) 
			{
				expectedRoomType = "Deluxe";
			}
		}

		assertEquals(expectedRoomType, booking.getRoomType(user));
	}

	private Object[] bookingTestData() 
	{
		return new Object[] {
				new Object[] { new User("John Doe", "VIP", false,0), true, true, "VIP",false },
				new Object[] { new User("Jane Doe", "normal", false,0), true, true, "Deluxe",false },
				new Object[] { new User("Alice", "non", false,0), true, true, "Standard" ,false},
				new Object[] { new User("Bob", "normal", true,0), true, true, "Deluxe" ,false},

		};
	}


	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "invalidUserTestData")
	public void testSetBooking_WithInvalidUser(User user) 
	{
		// Mock room behavior
		Room mockRoom = mock(Room.class);
		Printer mockPrinter = mock(Printer.class);
		Booking booking = new Booking(mockRoom, null, mockPrinter);

		// Call the method that is expected to throw IllegalArgumentException
		booking.setBooking(user);
	}


	private Object[] invalidUserTestData() 
	{
		return new Object[] {
				new Object[] { new User("", "normal", false,0) },
				new Object[] { new User("John Doe", null, false,0) },
				new Object[] { new User("John Doe", "invalid", false,0) },
				new Object[] { null }
		};
	}

	@Test
	public void testUpdateRoomCounter() {
	    Booking booking = new Booking(room, waitingList, printer);
	    
	    booking.updateRoomCounter(new User("John", "VIP", false, 0)); 
	   
	}


	// Test getting room type based on membership type
	@Test
	public void testGetRoomType() 
	{
		User vipUser = new User("Alice", "VIP", false,0);
		User normalUser = new User("Bob", "normal", false,0);
		User nonMemberUser = new User("Charlie", "non", false,0);

		when(room.checkRoom("VIP")).thenReturn(true);
		when(room.checkRoom("Deluxe")).thenReturn(true);
		when(room.checkRoom("Standard")).thenReturn(true);

		assertEquals("VIP", booking.getRoomType(vipUser));
		assertEquals("Deluxe", booking.getRoomType(normalUser));
		assertEquals("Standard", booking.getRoomType(nonMemberUser));
	}

	// Test handling maximum room bookings for each membership type
	@Test(expected = IllegalStateException.class)
	public void testMaxRoomBookingForVIP() {
	
	    User vipUser = new User("Eva", "VIP", false, 4);  
	    booking.getRoomType(vipUser);
	}

	//TEST CASE FOR ADDTOWAITINGLIST
	@Test
	public void testAddToWaitingList() 
	{
		// Create a mock user list
		ArrayList<User> userList = new ArrayList<>();
		User user1 = mock(User.class);
		User user2 = mock(User.class);

		when(user1.getName()).thenReturn("Adam");
		when(user2.getName()).thenReturn("Eve");

		when(user1.getMemberType()).thenReturn("VIP");
		when(user2.getMemberType()).thenReturn("member");



		userList.add(user1);
		userList.add(user2);

		// Call the method to add users to waiting list
		booking.addToWaitingList(userList);

		// Verify that addWaitingList is called for each user in the list
		verify(waitingList, times(1)).addWaitingList(user1);
		verify(waitingList, times(1)).addWaitingList(user2);
	}

	@Test
	public void testCancelBooking_UserNotFound() 
	{
		// Arrange
		User user = new User("John", "VIP", false,0);
		ArrayList<User> userList = new ArrayList<>();
		userList.add(user);

		// Stubbing behavior
		when(waitingList.getWaitingList("VIP")).thenReturn(userList);
		when(room.checkRoom(anyString())).thenReturn(true);

		// Act
		booking.cancelBooking(new User("Alice", "VIP", false,0));

		// Assert
		verify(room, never()).cancelBooking(anyString());
		verify(waitingList, never()).removeWaitingList(any(User.class));
	}


	@Test(expected = IllegalArgumentException.class)
	public void testSearchUser_NullUserList() 
	{
		booking.searchUser(null, "Alice");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSearchUser_NullName() 
	{
		// Act
		booking.searchUser(new ArrayList<>(), null);
	}

	@Test
	public void testSearchUser_UserNotFound() 
	{
		// Arrange
		ArrayList<User> userList = new ArrayList<>();
		userList.add(new User("John", "VIP", false,0));
		userList.add(new User("Alice", "normal", false,0));

		// Act
		int index = booking.searchUser(userList, "Bob");

		// Assert
		assertEquals(-1, index);
	}

	@Test
	public void testSearchUser_UserFound()
	{
		// Arrange
		ArrayList<User> userList = new ArrayList<>();
		userList.add(new User("John", "VIP", false,0));
		userList.add(new User("Alice", "normal", false,0));

		// Act
		int index = booking.searchUser(userList, "John");

		// Assert
		assertEquals(0, index);
	}
	// Test adding users to the waiting list with null user list
	@Test
	public void testAddToWaitingListWithNullList() 
	{
		booking.addToWaitingList(null);
		verify(waitingList, never()).addWaitingList(any(User.class));
	}
}

