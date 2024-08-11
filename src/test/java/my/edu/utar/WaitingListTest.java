package my.edu.utar;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class WaitingListTest {

    private WaitingList waitingList;
    private User vipUser;
    private User normalUser;
    private User nonMemberUser;

    //do initialisation first for waitingListm,VipUser,MemberUser,NonMemberUser
    @Before
    public void setUp() {
        waitingList = new WaitingList();

        vipUser = mock(User.class);
        when(vipUser.getMemberType()).thenReturn("VIP");

        normalUser = mock(User.class);
        when(normalUser.getMemberType()).thenReturn("normal");

        nonMemberUser = mock(User.class);
        when(nonMemberUser.getMemberType()).thenReturn("non-member");
    }
    
    // test cases for add vip user to waitingList
    @Test
    public void testAddVIPUserToWaitingList() {
        waitingList.addWaitingList(vipUser);
        ArrayList<User> vipList = waitingList.getWaitingList("VIP");
        assertTrue(vipList.contains(vipUser));
    }

    // test cases for remove vip user to waitingList
    //add into waitinglist then remove then check by whether it contain vipuser in the list
    @Test
    public void testRemoveVIPUserFromWaitingList() {
        waitingList.addWaitingList(vipUser);
        waitingList.removeWaitingList(vipUser);
        ArrayList<User> vipList = waitingList.getWaitingList("VIP");
        assertFalse(vipList.contains(vipUser));
    }
    
    //test cases to get vip waitinglist
    // use .size()
    @Test
    public void testGetVIPWaitingList() {
        waitingList.addWaitingList(vipUser);
        ArrayList<User> vipList = waitingList.getWaitingList("VIP");
        assertEquals(1, vipList.size());
    }

    //Repeat the same with vipuser above
    @Test
    public void testAddNormalUserToWaitingList() {
        waitingList.addWaitingList(normalUser);
        ArrayList<User> normalList = waitingList.getWaitingList("normal");
        assertTrue(normalList.contains(normalUser));
    }

    @Test
    public void testRemoveNormalUserFromWaitingList() {
        waitingList.addWaitingList(normalUser);
        waitingList.removeWaitingList(normalUser);
        ArrayList<User> normalList = waitingList.getWaitingList("normal");
        assertFalse(normalList.contains(normalUser));
    }

    @Test
    public void testGetNormalWaitingList() {
        waitingList.addWaitingList(normalUser);
        ArrayList<User> normalList = waitingList.getWaitingList("normal");
        assertEquals(1, normalList.size());
    }

    //repeat with the vip user
    @Test
    public void testAddNonMemberUserToWaitingList() {
        waitingList.addWaitingList(nonMemberUser);
        ArrayList<User> nonMemberList = waitingList.getWaitingList("non-member");
        assertTrue(nonMemberList.contains(nonMemberUser));
    }

    @Test
    public void testRemoveNonMemberUserFromWaitingList() {
        waitingList.addWaitingList(nonMemberUser);
        waitingList.removeWaitingList(nonMemberUser);
        ArrayList<User> nonMemberList = waitingList.getWaitingList("non-member");
        assertFalse(nonMemberList.contains(nonMemberUser));
    }

    @Test
    public void testGetNonMemberWaitingList() {
        waitingList.addWaitingList(nonMemberUser);
        ArrayList<User> nonMemberList = waitingList.getWaitingList("non-member");
        assertEquals(1, nonMemberList.size());
    }
    
    //TEST CASE FOR NULL USER IN AddWaitingList
    @Test(expected = IllegalArgumentException.class)
    public void testAddWaitingListForNullUser() 
    {
    	waitingList.addWaitingList(null);
    }
    
 // Test case for invalid member type in addWaitingList
    @Test(expected = IllegalArgumentException.class)
    public void testAddWaitingListForInvalidMemberType() {
        // Create a mocked user object with an invalid member type
        User invalidUser = mock(User.class);
        when(invalidUser.getMemberType()).thenReturn("invalid");

        // Add the invalid user to the waiting list
        waitingList.addWaitingList(invalidUser);
    }
    
    //Test case for null user for removeWaitingList
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWaitingListForNullUser() {
        waitingList.removeWaitingList(null);
    }
    
 // Test case for invalid member type in removeWaitingList
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWaitingListForInvalidMemberType() {
        // Create a mocked user object with an invalid member type
        User invalidUser = mock(User.class);
        when(invalidUser.getMemberType()).thenReturn("invalid");

        // Remove the invalid user from the waiting list
        waitingList.removeWaitingList(invalidUser);
    }
    //Test case for invalid member type for getwaitinglist
    @Test(expected = IllegalArgumentException.class)
    public void testGetWaitingListForInvalidMemberType() {
        waitingList.getWaitingList("invalid");
    }
}


