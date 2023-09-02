package uitest;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import uipages.HomePage;
import uipages.LoginPage;
import uipages.ProductDetailsPage;
import utilities.*;


@Listeners(TestListener.class)
public class ProductDetailsPageTest
{
	private WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;
	ProductDetailsPage productdetailsPage;
	private static ExtentTest EXTENT_TEST;
	
	@BeforeClass
	public void setUp() 
	{
		ConfigureDefaultProperties.setUpDefaultProperties(ProductDetailsPageTest.class);
		UIUtilities.lunchBrowser();
		driver =  UIUtilities.getDriver();
		homePage = new HomePage(driver);
		productdetailsPage = new ProductDetailsPage(driver);
		productdetailsPage = homePage.goProductDetailsPage();
	}

	@Test(priority = 1)
	public void pageTitleCheck()
	{
		LoggerUtilities.addInfoLog("Validate Page Title.");
		SoftAssertionUtilities.assertStringEquals("Radiant Tee", productdetailsPage.getTitle(), "Validate Product details Page Title.");
		SoftAssertionUtilities.assertAll();
	}

	@Test(priority = 2)
	public void addToCardWithSizeAndColorSelect()
	{
		productdetailsPage.productAddtoCard();
		boolean isDisplay = productdetailsPage.successMsgCheckAfterAddtoCard();
		SoftAssertionUtilities.assertBooleanEquals(isDisplay,true,"Success message not Displayed");
		SoftAssertionUtilities.assertAll();
	}

	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
}
