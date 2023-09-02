package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class UIElementUtility
{
	/*
	 * WebDriver instance
	 */
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
	public UIElementUtility(final WebDriver driver) 
	{
		this.driver = driver;
		waitUtils = new WaitUtility(driver);
	}

	/*
	 * Function getDriver which gets Webdriver object
	 * 
	 * return driver object
	 */
	public WebDriver getDriver()
	{
		return driver;
	}

	/*
	 * Wait Element and Click
	 * 
	 * @param element pass element here
	 */
	public void waitAndClick(final WebElement element)
	{
		try {
			waitUtils.waitForElementClickable(element);
			waitUtils.waitForElementVisible(element);
			element.click();
		}
		catch(final Exception e)
		{
			LoggerUtilities.addLog(e, "Exception coming from ElementUtility.waitAndClick() method");
		}
	}

	/*
	 * Handle locator type
	 * 
	 * @param locator : String
	 * return locator;
	 */

	public By byLocator(final String locator) 
	{
		String path;
		By result = null;
		if(locator.startsWith("//"))
		{
			result = By.xpath(locator);
		}
		else if(locator.startsWith("css=") || locator.startsWith("CSS="))
		{
			path = locator.replace("css=", "");
			path = locator.replace("CSS=", "");
			result = By.cssSelector(path);
		}
		else if(locator.startsWith("name=") || locator.startsWith("NAME="))
		{
			path = locator.replace("name=", "");
			path = locator.replace("NAME=", "");
			result = By.className(path);
		}
		else if(locator.startsWith("link=") || locator.startsWith("LINK="))
		{
			path = locator.replace("link=", "");
			path = locator.replace("LINK=", "");
			result = By.linkText(path);
		}
		else
		{
			result = By.cssSelector(locator);
		}

		return result;
	}
	/*
	 * This method is used to get the ByLocator of a web element which is already in the PageFactory
	 * 
	 * @param element for which ByLocator is needed
	 */

	public By getByLocator(final WebElement element) 
	{
		final String typeDescription = element.toString();
		final String locator;
		final String type;
		if(typeDescription.contains("->")) 
		{
			locator = typeDescription.split("->")[1].replaceFirst("(?s)(.*)\\]","$1" + "").split(":")[1].trim();
			type = typeDescription.split("->")[1].split(":")[0].trim();
		}
		else 
		{
			locator = typeDescription.split("->")[1].replaceFirst("(?s)(.*)\\]","$1" + "");
			type = typeDescription.split("->")[0].trim().split(":")[0];
		}
		if(type.contains("css"))
		{
			return (By.cssSelector(locator));
		}
		else if(type.contains("Xpath"))
		{
			return (By.xpath(locator));
		}
		else if(type.contains("id"))
		{
			return (By.id(locator));
		}
		else if(type.contains("name"))
		{
			return (By.name(locator));
		}
		else if(type.contains("tagName"))
		{
			return (By.tagName(locator));
		}
		else if(type.contains("linkText"))
		{
			return (By.linkText(locator));
		}
		else if(type.contains("partialLinkText"))
		{
			return (By.partialLinkText(locator));
		}
		else 
		{
			LoggerUtilities.addInfoLog("The object description locator type is invalid: "+ type);
			return null;
		}
	}
	/*
	 * click on element using element locator (Xpath, CSS)
	 * 
	 * @param locator : element locator
	 */
	public void clickOn(final String locator) 
	{
		final int time = 1000;
		final int maxIteration = 5;
		for(int iterator = 0; iterator < maxIteration; iterator++)
		{
			try 
			{
				final WebElement elem  = getDriver().findElement(byLocator(locator));
				elem.click();
			} 
			catch (final StaleElementReferenceException exception) 
			{
				waitUtils.sleepInSecond(time);
				LoggerUtilities.addLog(exception, "UIElementUtility.clickOn()");
			}
		}
	}

	/*
	 * Get Page title
	 * 
	 * return page title String
	 */
	public String getTitle() 
	{
		return driver.getTitle();
	}

	/*
	 * Get Page Source
	 * 
	 * return page source string
	 */
	public String getPageSource() 
	{
		return driver.getPageSource();
	}

	/*
	 * Enter text in to input box
	 * 
	 * @param element : Webelement object
	 * @param text : text to enter in input box
	 */
	public void inputText(final WebElement element, final String text)
	{
		waitUtils.waitForElementVisible(element);
		element.clear();
		element.sendKeys(text);
	}

	/*
	 * Select element by visible text
	 * 
	 * @param element: pass element here
	 */
	public void selectDropDownByText(final WebElement element, final String targetValue)
	{
		waitUtils.waitForElementVisible(element);
		new Select(element).selectByVisibleText(targetValue);
	}

	/*
	 * Select drop down value using index
	 * 
	 * @param element: WebElement object for drop down 
	 * @param Index : index of option
	 */
	public void selectDropDownByIndex(final WebElement element, final int index)
	{
		waitUtils.waitForElementVisible(element);
		new Select(element).deselectByIndex(index);
	}

	/*
	 * Select drop down value using value
	 * 
	 * @param element: WebElement object for drop down 
	 * @param Index : index of option
	 */
	public void selectDropDownByValue(final WebElement element, final String targetValue)
	{
		waitUtils.waitForElementVisible(element);
		new Select(element).selectByValue(targetValue);
	}

	public String getText(final WebElement element) 
	{
		waitUtils.waitForElementVisible(element);
		return element.getText();
	}

	public String getAttribute(final WebElement element, final String attributeName) 
	{
		waitUtils.waitForElementVisible(element);
		return element.getAttribute(attributeName);
	}

	public void dragAndDrop(final WebElement drag, final WebElement drop)
	{
		final Actions builder = new Actions(driver);
		waitUtils.waitForElementVisible(drag);
		waitUtils.waitForElementVisible(drop);
		final Action dragAndDrop = builder.clickAndHold(drag).moveToElement(drop).release(drop).build();
		dragAndDrop.perform();
	}

	public void clickOnElement(final WebElement element)
	{
		waitUtils.waitForElementVisible(element);
		final Actions builder = new Actions(driver);
		builder.moveToElement(element).click(element).build().perform();
	}

	public void rightClickOnElement(final WebElement element)
	{
		waitUtils.waitForElementVisible(element);
		final Actions builder = new Actions(driver);
		builder.moveToElement(element).contextClick(element).release().build().perform();
	}
	public void scrollToElement(final WebElement element) 
	{
		waitUtils.waitForElementVisible(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		waitUtils.sleepInSecond(1);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -250)");
		waitUtils.sleepInSecond(1);
	}
	public void clickOnLinkText(final String linkText) 
	{
		final String locator = "//a[text()='" + linkText + "']";
		waitUtils.waitForElement(locator);
		final WebElement element = driver.findElement(By.xpath(locator));
		scrollToElement(element);
		jsClick(element);
	}

	public void clickOnPartialLinkText(final String linkText) 
	{
		final String locator = "//a[contains(text()='" + linkText + "')]";
		waitUtils.waitForElement(locator);
		final WebElement element = driver.findElement(By.xpath(locator));
		scrollToElement(element);
		waitAndClick(element);
	}

	public void jsClick(final WebElement element) 
	{
		waitUtils.waitForElementVisible(element);
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public void scroolToBottom() 
	{
		final int time =3;
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
		waitUtils.sleepInSecond(time);
	}

	public String getCurrentURL() 
	{
		return driver.getCurrentUrl();
	}

	public void openURL(final String url)
	{
		driver.get(url);
	}

	public int getResponseCode(final String url) throws IOException {
		final URL  urlPath = new URL(url);
		final HttpsURLConnection huc = (HttpsURLConnection) urlPath.openConnection();
		huc.setRequestMethod("GET");
		huc.connect();
		final int code = huc.getResponseCode();
		return code;
	}

	public void switchToParentWindow() 
	{
		final List<Object> handels = driver.getWindowHandles().stream().collect(Collectors.toList());
		driver.switchTo().window((String) handels.get(0));
	}

	public void pressTab(final WebElement element) 
	{
		element.sendKeys(Keys.TAB);
	}

	public void clearField(final WebElement element) 
	{
		elements = waitUtils.waitForElementClickable(element);
		if(elements != null)
		{
			clickOnElement(element);
			element.sendKeys(Keys.SPACE, Keys.BACK_SPACE);
			final String keySequence = Keys.chord(Keys.CONTROL, "a");
			element.sendKeys(keySequence);
			element.sendKeys(Keys.DELETE);
		}
	}

	public void hoverOnElement(final WebElement elementToHoverOn, final WebElement elementToClick) 
	{
		elements = waitUtils.waitForElementVisible(elementToHoverOn);
		if(elements != null)
		{
			final Actions actions = new Actions(driver);
			actions.moveToElement(elementToHoverOn);
			actions.moveToElement(elementToClick);
			actions.click().build().perform();
		}
	}

	public boolean isElementDisplayed(final WebElement element) 
	{
		return elementSate = element.isDisplayed();
	}


	public boolean isElementEnabled(final WebElement element) 
	{
		if(isElementDisplayed(element))
		{
			elementSate = element.isEnabled();
		}

		return elementSate;
	}

	public void selectDropDownValueByText(final WebElement dropDownelement, final String option)
	{
		elements = waitUtils.waitForElementVisible(dropDownelement);
		if(elements != null)
		{
			dropDownelement.click();
			elements = driver.findElement(By.xpath("//mat-option[span[contains(text(),'"+ option +"')]]"));
			waitAndClick(elements);
		}
	}

	public void selectDropDownOptionByIndex(final WebElement dropDownelement, final int index) 
	{
		elements = waitUtils.waitForElementVisible(dropDownelement);
		if(elements != null)
		{
			dropDownelement.click();
			final List<WebElement> optionList = driver.findElements(By.xpath("//mat-option[@role='option']"));
			if(!optionList.isEmpty())
			{
				waitAndClick(optionList.get(index));	
			}
		}
	}
	
	public void enterText(final WebElement element,final String text,final String attributeValue,final boolean pressTab) 
	{
		elements = waitUtils.waitForElementClickable(element);
		if(elements!= null) 
		{
			final String fieldValues = getAttribute(element, attributeValue);

			if( !(fieldValues.isBlank() ||  fieldValues.isEmpty() )) 
			{
				clearField(element);
			}

			clickOnElement(element);
			element.sendKeys(text);

			if(pressTab)
			{
				pressTab(element);
			}
		}

	}
	
	public void jsEnterText(final WebElement element,final String text,final boolean pressTab)
	{
		elements = waitUtils.waitForElementClickable(element);
		final Actions action = new Actions(driver);
		final JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].value = '';", element);
		action.sendKeys(element, text).build().perform();
		
		if(pressTab)
		{
			pressTab(element);
		}
	}

	public void clickOnAllCheckBoxes(final List<WebElement> checkboxElements) 
	{
		if(!checkboxElements.isEmpty())
		{
			for(final WebElement checkbox : checkboxElements)
			{
				if(!checkbox.isSelected()) 
				{
					waitAndClick(checkbox);
				}
			}
		}
	}
	
	public List<String> getAllCheckBoxLabelText(final List<WebElement> checkboxElements)
	{
		final List<String> checkBoxLabelList = new ArrayList<String>();
		if(!checkBoxLabelList.isEmpty()) 
		{
			for(final WebElement checkbox : checkboxElements)
			{
				UIUtilities.waitForElement(checkbox);
				final String checkBoxLabel = getText(checkbox);
				checkBoxLabelList.add(checkBoxLabel);
			}
		}
		return checkBoxLabelList;
	}
	
	public void clickRandomCheckBox(final List<WebElement> listOfcheckboxs) 
	{
		if(!listOfcheckboxs.isEmpty()) 
		{
			final int noOfCheckBoxes = listOfcheckboxs.size();
			final int minimum = 1;
			final int randomNumber = (int) (Math.random() * (noOfCheckBoxes - minimum)) + minimum;
			elements = listOfcheckboxs.get(randomNumber);
			waitAndClick(elements);
		}
	}

}
