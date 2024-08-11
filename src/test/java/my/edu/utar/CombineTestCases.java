package my.edu.utar;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    BookingTest.class,
    UserTest.class,
    WaitingListTest.class
})
public class CombineTestCases {
    // This class doesn't need any code, it only serves as a holder for the annotations
}
