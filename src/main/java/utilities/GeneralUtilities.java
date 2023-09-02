package utilities;

public final class GeneralUtilities {

	private GeneralUtilities()
	{
	}

	private static String USERNAME;
	private static String PASSWORD;
	private static String APIBASEURI;
	private static String APIPORT;
	private static String UIBROWSER;
	private static String UIBASEURL;
	private static String UIUSERNAME;
	private static String UIPASSWORD;
	private static String FILELOCATION;

	private static boolean HEADLESS;

	public static void setApiBaseURI(final String apiUrlValue) {
		APIBASEURI = apiUrlValue;
	}

	public static String getApiBaseURI() {
		return APIBASEURI;
	}

	public static void setApiPort(final String apiPortValue) {
		APIPORT = apiPortValue;
	}



	public static void setUserName(final String userNameValue) {
		USERNAME = userNameValue;
	}

	public static void setPassword(String passwordValue) {
		PASSWORD = passwordValue;
	}

	public static String getUiBrowser()
	{
		return UIBROWSER;
	}

	public static void setUiBrowser(final String browserName)
	{
		UIBROWSER = browserName;
	}

	public static String getUiBaseURL()
	{
		return UIBASEURL;
	}

	public static void setUiBaseURL(final String browserURL)
	{
		UIBASEURL = browserURL;
	}

	public static void setUIUserName(final String uiUserName) {
		UIUSERNAME = uiUserName;
	}

	public static void setUIPassword(String uiPassword) {
		UIPASSWORD = uiPassword;
	}

	public static String getUiUserName()
	{
		return UIUSERNAME;
	}
	public static String getUiPassword()
	{
		return UIPASSWORD;
	}

	public static void setHeadless(final boolean headless) 
	{
		HEADLESS = headless;
	}

	public static boolean isHeadless() 
	{
		return HEADLESS;
	}

	public static void setFileLocation(final String filePath) {
		FILELOCATION = filePath; 
	}

	public static String getFileLocation()
	{
		return FILELOCATION;
	}

}
