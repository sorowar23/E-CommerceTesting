package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtility 
{
	/*
	 * Timeout window
	 */
	private long timeout = 10;

	private int retry = 3;

	private UIElementUtility elementUtil;

	private final WebDriver driver;

	private final WebDriverWait wait;
	
	/*
	 * Constructor for WaitUtility to set driver object
	 */
	public WaitUtility(final WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	}
	
	/*
	 * set wait timeout window based on application
	 * 
	 * @param timeInSeconds pass timeout here
	 */
	public void setWaitTimeoutForApplication(final long timeInSeconds)
	{
		this.timeout = timeInSeconds;
	}
	
	/*
	 * Wait for element to clickable
	 * 
	 * @param element pass element here
	 */
	public WebElement waitForElementClickable( final WebElement element)
	{
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout, 1));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	/*
	 * Wait for element to be present
	 * 
	 * @param element pass element here
	 */
	public WebElement waitForElementVisible( final WebElement element)
	{
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout, 1));
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	/*
	 * Wait for element to be present
	 * 
	 * @param locator : String
	 */
	public void waitForElement( final String locator) 
	{
		final int time = 1000;
		final UIElementUtility eleUtils = new  UIElementUtility(driver);
		for(int iterator = 0; iterator < timeout; iterator++ )
		{
			try 
			{
				driver.findElement(eleUtils.byLocator(locator));
				Thread.sleep(time);
				break;
			}
			catch (final Exception e) 
			{
				LoggerUtilities.addLog(e, "WaitUtility.waitForElement();");
			}
		}
	}
	
	/*
	 * Make driver object sleep
	 * 
	 * @param seconds : int
	 */
	public void sleepInSecond(final int seconds) 
	{
		final int time = seconds * 1000;
		
		try 
		{
			Thread.sleep(time);
		}
		catch(final InterruptedException e)
		{
			LoggerUtilities.addLog(e, "WaitUtility.sleepInSecond();");
		}
	}
   /*
    This method to wait for an element to be visible, this only for PageFactory web elements.
    */
	public void waitForElementToBeLocated(final WebElement element)
	{
		if(elementUtil == null)
		{
			elementUtil = new UIElementUtility(this.driver);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(elementUtil.getByLocator(element)));
	}
	/*
	 * Make driver object sleep
	 * 
	 * @param seconds : int
	 */
	public void sleepInMiliSeconds(final int miliSeconds) 
	{
		final int time = miliSeconds;
		
		try 
		{
			Thread.sleep(time);
		}
		catch(final InterruptedException e)
		{
			LoggerUtilities.addLog(e, "WaitUtility.sleepInMiliSeconds();");
		}
	}
}
