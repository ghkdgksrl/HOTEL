package my.edu.utar;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BookingIntegrationTest {

    private Room room;
    private WaitingList waitingList;
    private Printer printer;
    private Booking booking;

    @Before
    public void setUp() {
        room = mock(Room.class);
        waitingList = mock(WaitingList.class);
        printer = mock(Printer.class);
        booking = new Booking(room, waitingList, printer);

    }

    @Test
    public void testSuccessfulBooking() {
        // Set up mock behaviors
        when(room.checkRoom("VIP")).thenReturn(true);

        // Perform booking
        User user = new User("John Doe", "VIP", false,0);
        booking.setBooking(user);

        // Verify interactions
    
        verify(printer, times(1)).printInfo(eq("John Doe"), eq("VIP"), eq("VIP"));
    }

    @Test
    public void testWaitingList() {
        // Set up mock behaviors
        when(room.checkRoom("VIP")).thenReturn(false);
        
        // Create a list of mock users waiting for the "VIP" room type
        ArrayList<User> waitingUsers = new ArrayList<>();
        waitingUsers.add(new User("Alice", "VIP", false,0));
        waitingUsers.add(new User("Bob", "VIP", false,0));
        
        // Stub the getWaitingList method to return the list of waiting users
        when(waitingList.getWaitingList("VIP")).thenReturn(waitingUsers);

        // Perform booking
        User user = new User("Jane", "VIP", false,3);
        booking.setBooking(user);
        // Verify interactions
        verify(waitingList, times(0)).getWaitingList("VIP");
        verifyNoMoreInteractions(waitingList);
    }


    @Test
    public void testGetRoomType_NormalUserWithReward() {
        // Set up mock behaviors
        when(room.checkRoom("VIP")).thenReturn(true);
        when(room.checkRoom("Deluxe")).thenReturn(true);
        when(room.checkRoom("Standard")).thenReturn(true);

        // Perform booking
        User user = new User("Alice", "normal", true,0);
        String roomType = booking.getRoomType(user);

        // Verify interactions
        assertEquals("VIP", roomType);
    }

    // Additional test cases
    @Test
    public void testGetRoomType_NormalUserWithNoReward() {
        // Set up mock behaviors
        when(room.checkRoom("VIP")).thenReturn(false);
        when(room.checkRoom("Deluxe")).thenReturn(true);
        when(room.checkRoom("Standard")).thenReturn(true);

        // Perform booking
        User user = new User("Charlie", "normal", false,0);
        String roomType = booking.getRoomType(user);

        // Verify interactions
        assertEquals("Deluxe", roomType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRoomType_RewardUserWithNoRoomsAvailable() {
        // Set up mock behaviors
        when(room.checkRoom("Standard")).thenReturn(false);

        // Perform booking
        User user = new User("David", "reward", true,0);
        booking.getRoomType(user);
    }
}

