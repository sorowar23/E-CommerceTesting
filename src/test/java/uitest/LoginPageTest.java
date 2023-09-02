package uitest;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uipages.MyAccountPage;
import uipages.HomePage;
import uipages.LoginPage;
import utilities.*;

public class LoginPageTest
{
    private WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    MyAccountPage dashboardPage;
    private static ExtentTest EXTENT_TEST;

    @BeforeClass
    public void setUp()
    {
        ConfigureDefaultProperties.setUpDefaultProperties(LoginPageTest.class);
        UIUtilities.lunchBrowser();
        driver =  UIUtilities.getDriver();
        homePage = new HomePage(driver);
        loginPage = homePage.selectLoginMenu();

    }

    @Test(priority = 1)
    public void signInWithValidInfo()
    {
        LoggerUtilities.addInfoLog("Sign In With Valid Creds");
        homePage =	loginPage.login(GeneralUtilities.getUiUserName(), GeneralUtilities.getUiPassword());
        SoftAssertionUtilities.assertStringEquals("Welcome, Demo User!", homePage.getUserName(), "Validate Expected Author Name Exist.");
        SoftAssertionUtilities.assertAll();
    }

    @AfterClass
    public void tearDown()
    {
        driver.quit();
    }
}
