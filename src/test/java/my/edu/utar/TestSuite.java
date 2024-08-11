package my.edu.utar;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value= Suite.class)
@SuiteClasses(value=
{
		BookingTest.class,
		UserTest.class,
		WaitingListTest.class,
		CombineTestCases.class,
		BookingIntegrationTest.class
})

public class TestSuite 
{

}
