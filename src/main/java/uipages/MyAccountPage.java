package uipages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.UIElementUtility;

public class MyAccountPage
{
	private static final Logger LOG = LoggerFactory.getLogger(MyAccountPage.class);
	
	/*
	 * Login Button WebElement
	 */
	@FindBy(css="h4.author__title")
	private static WebElement AUTHORNAME;
	//a[@id='ui-id-20']//span[contains(text(),'Hoodies & Sweatshirts')]
	private final WebDriver driver;
	private final UIElementUtility elementUtility;
	
	public MyAccountPage(final WebDriver driver) {
		this.driver = driver;
		this.elementUtility = new UIElementUtility(this.driver);
		PageFactory.initElements(this.driver, this);
	}
	public  String getAuthorName()
	{
		 String authorNameString = AUTHORNAME.getText();
		 return authorNameString;
	}
}
