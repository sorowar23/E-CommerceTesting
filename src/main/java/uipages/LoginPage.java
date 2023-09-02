package uipages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.WaitUtility;

public class LoginPage 
{
	private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);
	@FindBy(xpath="//input[@id='email']")
	private static WebElement USERNAMEFIELD;

	@FindBy(xpath="//fieldset[@class='fieldset login']//input[@id='pass']")
	private static WebElement PASSWORDFIELD;
	
	@FindBy(xpath="//fieldset[@class='fieldset login']//span[contains(text(),'Sign In')]")
	private static WebElement LOGINBTN;
	
	private static WebElement ERRORMSG;
	
	private final WebDriver driver;
	private final WaitUtility waitUtils;
	public LoginPage(final WebDriver driver) 
	{
		this.driver = driver;
		this.waitUtils = new WaitUtility(this.driver);
		PageFactory.initElements(this.driver, this);
	}
	
	private static WebElement getUserNameField()
	{
		return USERNAMEFIELD;
	}
	
	private static WebElement getPasswordField()
	{
		return PASSWORDFIELD;
	}
	
	private static WebElement getLoginBtn()
	{
		return LOGINBTN;
	}
	
	public HomePage login(final String uname, final String pwd)
	{
		getUserNameField().clear();
		getUserNameField().sendKeys(uname);
		getPasswordField().clear();
		getPasswordField().sendKeys(pwd);
		getLoginBtn().click();
		return new HomePage(driver);
	}
}
