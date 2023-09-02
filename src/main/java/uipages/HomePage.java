package uipages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.UIElementUtility;
import utilities.UIUtilities;
import utilities.WaitUtility;
import java.util.List;

public class HomePage
{
	private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);
	/*
	 * WebElement of Logo
	 */
	@FindBy(css = ".logo img")
	private WebElement homeLogo;
	/*
	 WebElement of Featured Items
	 */
	@FindBy(css="ol.product-items li")
	private List<WebElement> featuredItemsList;
	/*
 	WebElement of contact us link
 	*/
	@FindBy(xpath="//a[text()='Contact us']")
	private WebElement contactUsLink;
	/*
     WebElement of firstProductItem
     */
	@FindBy(css=".product-items>li:first-of-type")
	private WebElement firstProductItem;
	/*
	  WebElement of firstProductItem Add to Card button
	 */
	@FindBy(css=".product-items>li:first-of-type .tocart")
	private WebElement addToCardButton;
	/*
	  WebElement of signIn Button
	 */
	@FindBy(xpath="//div[@class='panel header']//a[contains(text(),'Sign In')]")
	private WebElement loginButton;
	/*
	  WebElement of loggedIn level
	 */
	@FindBy(css="div[class='panel header'] span[class='logged-in']")
	private WebElement loggedIn;
	private final WebDriver driver;
	private final UIElementUtility elementUtility;
	private final WaitUtility waitUtils;
	public HomePage(final WebDriver driver)
	{
		this.driver = driver;
		this.elementUtility = new UIElementUtility(this.driver);
		this.waitUtils = new WaitUtility(this.driver);
		PageFactory.initElements(this.driver, this);
	}
	public String getTitle() 
	{
		return driver.getTitle();
	}
	/*
	 * Method to validate logo
	 */
	public Boolean homePageLogoCheck()
	{
		boolean isDisplayed = homeLogo.isDisplayed();
		return isDisplayed;
	}
	/*
	 * Method to validate contact us link
	 */
	public Boolean contactUsLinkCheck()
	{
		boolean isDisplayed = contactUsLink.isDisplayed();
		return isDisplayed;
	}
	public String getFeaturedItemCount()
	{
		int count = featuredItemsList.size();
		String itemCount = String.valueOf(count);
		return itemCount;
	}
	/*
	 * Method to validate contact us link
	 */
	public Boolean addToCardButtonCheck()
	{
		boolean isDisplayed = addToCardButton.isDisplayed();
		return isDisplayed;
	}
	public ProductDetailsPage goProductDetailsPage()
	{
		firstProductItem.click();
		return new ProductDetailsPage(driver);
	}
	/*
	 * Method to select login menu
	 */
	public LoginPage selectLoginMenu()
	{
		UIUtilities.waitForElement(loginButton);
		loginButton.click();
		return new LoginPage(driver);
	}
	public String getUserName()
	{
		waitUtils.sleepInSecond(1);
		String userName = loggedIn.getText();
		return userName;
	}
}