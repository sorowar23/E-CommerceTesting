package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public final class ConfigureDefaultProperties{

    private ConfigureDefaultProperties()
    {
    }

    private static String ENV ="dev";
   
    private static Properties PROPERTIES = new Properties();

    public static String getENV() {
        return ENV;
    }

    public static void setENV(final String env) {
        ENV = env;
    }
    public static void setUpDefaultProperties( final Class<?> clazz) 
    {
    	InputStream input = null;
    	try 
    	{
    		input = Files.newInputStream(Paths.get("config.properties"));
    		final Properties prop = new Properties();
    		prop.load(input);
    		// setUpEnvironment(prop);
    		// setUpAccessTokenUserDetails(prop);
    		setUpAPIDetails(prop);
    		setUpUIDetails(prop);

    	}
    	catch(final IOException ex)
    	{
    		ex.fillInStackTrace();
    	}
    	finally 
    	{
			if (input != null) 
			{
				try 
				{
					input.close();
				}
				catch (final IOException e) 
				{
					// TODO: handle exception
				}
			}
		}
    }

    private static void setUpEnvironment(final Properties prop){
        if(prop.getProperty("api.environment") != null){
            setENV(prop.getProperty("api.environment"));
        }
    }

    private static void setUpAccessTokenUserDetails(final Properties prop){
        if(prop.getProperty("accessToken.userName") != null){
        	GeneralUtilities.setPassword(prop.getProperty("accessToken.userName"));
        }
        if(prop.getProperty("accessToken.password") != null){
        	GeneralUtilities.setPassword(prop.getProperty("accessToken.password"));
        }
    }
    private static void setUpAPIDetails(final Properties prop){
        if(prop.getProperty("api.baseURI") != null){
            GeneralUtilities.setApiBaseURI(prop.getProperty("api.baseURI"));
        }
       /* if(prop.getProperty("api.port") != null){
            setApiPort(prop.getProperty("api.port"));
        }*/

    }
    
    private static void setUpUIDetails(final Properties prop) 
    {
    	if(prop.getProperty("ui.browser") != null){
    		GeneralUtilities.setUiBrowser(prop.getProperty("ui.browser"));
    	}
    	if(prop.getProperty("ui.baseURL") != null){
    		GeneralUtilities.setUiBaseURL(prop.getProperty("ui.baseURL"));
    	}
    	if((prop.getProperty("ui.headless") != null) 
    			&& ( (prop.getProperty("ui.headless").equalsIgnoreCase("false") ) 
    					|| (prop.getProperty("ui.headless").equalsIgnoreCase("true") ) ))
    	{
    		GeneralUtilities.setHeadless(Boolean.parseBoolean(prop.getProperty("ui.headless")));
    	}
    	if(prop.getProperty("ui.userName") != null){
    		GeneralUtilities.setUIUserName(prop.getProperty("ui.userName"));
    	}
    	if(prop.getProperty("ui.password") != null){
    		GeneralUtilities.setUIPassword(prop.getProperty("ui.password"));
    	}
    }

}
