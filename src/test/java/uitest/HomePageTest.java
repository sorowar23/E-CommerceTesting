package uitest;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import uipages.ProductDetailsPage;
import uipages.HomePage;
import uipages.LoginPage;
import utilities.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

public class HomePageTest
{
	private WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;
	ProductDetailsPage productPage;
	private static ExtentTest EXTENT_TEST;

	@BeforeClass
	public void setUp() 
	{
		ConfigureDefaultProperties.setUpDefaultProperties(HomePageTest.class);
		UIUtilities.lunchBrowser();
		driver =  UIUtilities.getDriver();
		homePage = new HomePage(driver);
		productPage = new ProductDetailsPage(driver);
	}

	@Test(priority = 1)
	public void pageTitleCheck()
	{
		LoggerUtilities.addInfoLog("Validate Page Title.");
		SoftAssertionUtilities.assertStringEquals("Home Page", homePage.getTitle(), "Validate Page Title.");
		SoftAssertionUtilities.assertAll();
	}

	@Test(priority = 2)
	public void homePageLogoCheck()
	{
		LoggerUtilities.addInfoLog("Validating Home page logo Exist or Not");
		boolean isDisplay = homePage.homePageLogoCheck();
		System.out.println("Logo Exist::"+ isDisplay);
		SoftAssertionUtilities.assertBooleanEquals(isDisplay,true,"Logo not Displayed");
		SoftAssertionUtilities.assertAll();
	}

	@Test(priority = 3)
	public void featuredItemCount()
	{
		String itemCount = homePage.getFeaturedItemCount();
		SoftAssertionUtilities.assertStringEquals(itemCount,"6","Featured Item Count not correct");
		SoftAssertionUtilities.assertAll();
	}

	@Test(priority = 4)
	public void contactUsLinkCheck()
	{
		LoggerUtilities.addInfoLog("Validating Home page logo Exist or Not");
		boolean isDisplay = homePage.contactUsLinkCheck();
		SoftAssertionUtilities.assertBooleanEquals(isDisplay,true,"Contact us link not Displayed");
		SoftAssertionUtilities.assertAll();
	}

	@Test(priority = 5)
	public void addToCardButtonCheck()
	{
		LoggerUtilities.addInfoLog("Validating addToCard Button in Home page product Exist or Not");
		boolean isDisplay = homePage.addToCardButtonCheck();
		SoftAssertionUtilities.assertBooleanEquals(isDisplay,true,"addToCard button not Displayed in home page product items");
		SoftAssertionUtilities.assertAll();
	}

	@Test(priority = 6)
	public void productDetailsPageCheck()
	{
		LoggerUtilities.addInfoLog("Validating product details page link");
		productPage = homePage.goProductDetailsPage();
		SoftAssertionUtilities.assertStringEquals("Radiant Tee", productPage.getTitle(), "Page title not correct in product details page.");
		SoftAssertionUtilities.assertAll();
	}

	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
}
