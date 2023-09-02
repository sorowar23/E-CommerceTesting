package endpoints;

import utilities.GeneralUtilities;

public final class UserHelper 
{
	private UserHelper() 
	{
		
	}
	
	public static void setAPIBaseURI()
	{
		GeneralUtilities.setApiBaseURI("https://reqres.in");
	}
	
	public static String getUserRecords()
	{
		setAPIBaseURI();
		return "/api/users/";
	}
	
	public static String postUserRecord()
	{
		setAPIBaseURI();
		return "/api/users/";
	}
}
