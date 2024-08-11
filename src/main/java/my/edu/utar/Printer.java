package my.edu.utar;

public class Printer 
{
	public void printInfo(String name, String memberType, String roomType) 
	{
		System.out.println();
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("~~~~~~~~~~~~~~~~    TWICE HOTEL    ~~~~~~~~~~~~~~~~");
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println();
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("		Booking Information");
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println();
		System.out.println("	  NAME		 :	" + name.toUpperCase());
		System.out.println("	  MEMBER TYPE	 :	" + memberType.toUpperCase());
		System.out.println("	  ROOM TYPE	 :	" + roomType.toUpperCase());
		System.out.println();
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	}
}