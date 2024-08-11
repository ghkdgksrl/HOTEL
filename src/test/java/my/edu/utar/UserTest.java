package my.edu.utar;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User("Tyson", "normal", true, 0);
    }

    // Test for getName
    @Test
    public void testGetName() {
        String expected = "Tyson";
        String actual = user.getName();
        assertEquals(expected, actual);
    }

    // Test for setName by setting name to "Justin" and comparing it using getName
    @Test
    public void testSetName() {
        String expected = "Justin";
        user.setName("Justin");
        String actual = user.getName();
        assertEquals(expected, actual);
    }

    // Test getMemberType
    @Test
    public void testGetMemberType() {
        String expected = "normal";
        String actual = user.getMemberType();
        assertEquals(expected, actual);
    }

    // Test setMemberType
    @Test
    public void testSetMemberType() {
        String expected = "VIP";
        user.setMemberType("VIP");
        String actual = user.getMemberType();
        assertEquals(expected, actual);
    }

    // Test getReward
    @Test
    public void testGetReward() {
        user.setReward(true);
        assertTrue(user.getReward());
    }

    // Test setReward
    @Test
    public void testSetReward() {
        user.setReward(false);
        assertFalse(user.getReward());
    }

    // Test setting name to null
    @Test(expected = IllegalArgumentException.class)
    public void testSetNameForNull() {
        user.setName(null);
    }

    // Test setting empty value for setName
    @Test(expected = IllegalArgumentException.class)
    public void testSetNameForEmptyValue() {
        user.setName("");
    }

    // Test setting invalid member type
    @Test(expected = IllegalArgumentException.class)
    public void testSetMemberTypeInvalidInput() {
        user.setMemberType("Joker");
    }

    // Test updating room booked count
    @Test
    public void testUpdateRoomBooked() {
        user.updateRoomBooked(3);
        assertEquals(3, user.getRoomBooked());
    }

    // Test getting room booked count
    @Test
    public void testGetRoomBooked() {
        user.updateRoomBooked(5);
        assertEquals(5, user.getRoomBooked());
    }

}