package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class WindowUtility 
{
	private final WebDriver driver;

	/*
	 * WebDriver instance
	 */
	private final  WaitUtility waitUtils;

	/*
	 * WebElement instance
	 */
	private WebElement elements;

	/*
	 * Boolean instance
	 */
	private boolean elementSate;

	/*
	 * Constructor for UIElementUtility to set driver object
	 */
	public WindowUtility(final WebDriver driver) 
	{
		this.driver = driver;
		waitUtils = new WaitUtility(driver);
	}
	
	public void closeAllWindow(final List<String> windowList)
	{
		UIUtilities.waitForWindows(windowList.size() + 1);
		final String parent;
		parent = driver.getWindowHandle();
		final Set<String> windowHandles = driver.getWindowHandles();
		final Iterator<String> itr = windowHandles.iterator();
		
		while(itr.hasNext()) 
		{
			final String childWindow = itr.next();
			if(!parent.equals(childWindow))
			{
				driver.switchTo().window(childWindow);
				if(windowList.contains(driver.getTitle())) 
				{
					driver.close();
				}
			}
			driver.switchTo().window(parent);
		}
	}
	
	public boolean switchWindow(final String title) 
	{
		final String mainWindowHandle;
		mainWindowHandle = driver.getWindowHandle();
		final Set<String> handles = driver.getWindowHandles();
		
		for(final String handle : handles) 
		{
			driver.switchTo().window(handle);
			if(driver.getTitle().equals(title))
			{
				return true;
			}
		}
		driver.switchTo().window(mainWindowHandle);
		return false;
	}

	public void maximizeAllWindow(final List<String> windowList)
	{
		final String  sParentWindow = driver.getWindowHandle();
		final Set<String> windowHandles = driver.getWindowHandles();
		final Iterator<String> itr = windowHandles.iterator();

		while(itr.hasNext()) 
		{
			final String sChildWindow = itr.next();
			if(!sParentWindow.equals(sChildWindow))
			{
				driver.switchTo().window(sChildWindow);
				driver.manage().window().maximize();

			}
			driver.switchTo().window(sChildWindow);
		}
	}
	
	public Set<String> getAllWindows()
	{
		final Set<String> setOfWindowSet = driver.getWindowHandles();
		return setOfWindowSet;
	}
	
	public boolean checkIfFormExists(final String expectedFormName)
	{
		boolean result = false;
		final Set<String> listOfWindow = getAllWindows();
		
		if(!(listOfWindow.isEmpty() ))
		{
			if(listOfWindow.contains(expectedFormName))
			{
				result = true;
			}
		}
		else 
		{
			LoggerUtilities.addErrorLog("List of windows is Null/Empty");
		}
		
		return result;
	}
	
}
