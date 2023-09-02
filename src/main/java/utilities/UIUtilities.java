package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Locale;
import java.util.logging.Level;


public final class UIUtilities {

	private UIUtilities() {

	}


	private static String BASEPATH;

	public static final long PAGE_LOAD_TIMEOUT = 50;

	public static final long IMPLICIT_WAIT = 50;

	public static final long EXPLICIT_WAIT = 40;

	private static WebDriverWait WAIT;

	private static File SRCFILE;

	private static final String CURRENTDIR = System.getProperty("user.dir");

	private static String DESTINATION = "";

	private static WebDriver DRIVER;

	private static ChromeDriverService SERVICE;

	private static ChromeOptions CHROMEOPTIONS;


	public static WebDriver getDriver()
	{
		return DRIVER;
	}

	public static ChromeDriverService getService() 
	{
		return SERVICE;
	}

	public static void setDriver(final WebDriver driver) 
	{
		UIUtilities.DRIVER = driver;
	}

	public static void setService(final ChromeDriverService service) 
	{
		UIUtilities.SERVICE = service;

	}

	public static WebDriverWait getWebDriverWait() {
		WAIT = new   WebDriverWait(getDriver(), Duration.ofSeconds(EXPLICIT_WAIT, 1));
		return WAIT;
	}

	public static String getBasePath() 
	{
		try 
		{
			BASEPATH = new File(".").getCanonicalPath();
		} 
		catch (final Exception ex) 
		{
			LoggerUtilities.addLog(ex, "UIUitilities.getBasePath()");

		}

		return BASEPATH;
	}

	public static void lunchBrowser()
	{
		setUpDriver();

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
		getDriver().get(GeneralUtilities.getUiBaseURL());
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
		//System.out.println("baseURL:::"+GeneralUtilities.getUiBaseURL());
	}

	public static void webDriverPulseCheck() 
	{
		if(!SERVICE.isRunning()) {
			DRIVER.quit();
			LoggerUtilities.addErrorLog("The ChromeDriver Service has Stopped.");
		}
	}

	public static void webDriverQuit()
	{
		DRIVER.quit();
	}

	private static void setUpDriver() 
	{
		final String browserName = GeneralUtilities.getUiBrowser();
		String directory = GeneralUtilities.getFileLocation();
		final String operatingSystem = System.getProperty("os.name").toLowerCase(Locale.ENGLISH); 

		/*System.out.println("BROWSER NAME: "+ browserName);
		System.out.println("operatingSystemName: "+ operatingSystem);
		System.out.println("directory: "+ directory);
		*/
		if("chrome".equalsIgnoreCase(browserName))
		{
			CHROMEOPTIONS = new ChromeOptions();
			final LoggingPreferences logs = new LoggingPreferences();
			logs.enable(LogType.PERFORMANCE, Level.WARNING);
			logs.enable(LogType.BROWSER, Level.WARNING);
			logs.enable(LogType.DRIVER, Level.WARNING);
			CHROMEOPTIONS.setCapability(CapabilityType.LOGGING_PREFS, logs);
			CHROMEOPTIONS.addArguments("--disable-web-security");
			CHROMEOPTIONS.addArguments("--window-size=1920,1200");
			CHROMEOPTIONS.addArguments("--start-maximize");
			if(operatingSystem.contains("windows"))
			{
				System.setProperty("webdriver.chrome.driver", getBasePath() + File.separator + "chromedriver.exe");
				CHROMEOPTIONS.setHeadless(GeneralUtilities.isHeadless());
			}
			else 
			{
				WebDriverManager.chromedriver().setup();
				System.setProperty("webdriver.chrome.driver", getBasePath() + File.separator + "chromedriver");
				System.setProperty("webdriver.chrome.whitelistedIps", "");
				CHROMEOPTIONS.addArguments("--no-sandbox")
				.addArguments("--disable-software-rasterizer")
				.addArguments("--cap-add=SYS_ADMIN")
				.addArguments("--disable-gpu")
				.addArguments("--cap-drop=SETPCAP")
				.addArguments("--cap-drop=AUDIT_WRITE")
				.addArguments("--cap-drop=DAC_OVERRIDE")
				.addArguments("--cap-drop=SETGID")
				.addArguments("--cap-drop=SETUID")
				.addArguments("--disable-extenstions")
				.addArguments("--whitelisted-ips=''")
				.addArguments("--no-proxy-server")
				.addArguments("--ignore-certificate-errors")
				.addArguments("-force-device-scale-factor=1")
				.addArguments("--no-first-run")
				.addArguments("--no-default-browser-check")
				.addArguments("--enable-logging")
				.addArguments("--log-level=1")
				.addArguments("--disable-dev-shm-usage");
				CHROMEOPTIONS.setHeadless(GeneralUtilities.isHeadless());

				final ChromeDriverService service = new ChromeDriverService.Builder()
						.withLogFile(new File(String.valueOf(Paths.get(directory,"debug.log"))) )
						.withLogLevel(ChromeDriverLogLevel.fromLevel(Level.WARNING))
						.withAppendLog(true)
						.build();
				setDriver(new ChromeDriver(CHROMEOPTIONS));
			}


			//setService(service);
			//setDriver(new ChromeDriver(options));
		}
		else if("firefox".equalsIgnoreCase(browserName))
		{
			if(operatingSystem.contains("windows"))
			{
				System.setProperty("webdriver.gecko.driver", getBasePath() + File.separator + "geckodriver.exe");
			}
			else 
			{
				System.setProperty("webdriver.gecko.driver", getBasePath() + File.separator + "geckodriver");
			}

			final FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(GeneralUtilities.isHeadless());

			setDriver(new FirefoxDriver(options));
		}
	}

	public static String takeScreenShotAtEndOfTest(final WebDriver driver, final String screenshotName)
	{
		try 
		{
			SRCFILE = (( TakesScreenshot) driver ).getScreenshotAs(OutputType.FILE);
			String timestamp = TimeUtilities.getCompleteDateWithTimeAndTimeZone("US/Eastern", 0);
			DESTINATION = CURRENTDIR + File.separator + "output" + File.separator + "screenshots" + File.separator 
					+ timestamp + ".png";
			FileHandler.copy(SRCFILE, new File(DESTINATION));		
		} 
		catch (final IOException e)
		{
			LoggerUtilities.addLog(e, "UIUitilities.takeScreenShotAtEndOfTest()");
		}

		return DESTINATION;
	}

	public static void waitForElement(final WebElement element) 
	{
		final String typeDescription = element.toString().trim();
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
			getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
		}
		else if(type.contains("Xpath"))
		{
			getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		}
		else if(type.contains("id"))
		{
			getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
		}
		else if(type.contains("name"))
		{
			getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.name(locator)));
		}
		else if(type.contains("tagName"))
		{
			getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locator)));
		}
		else if(type.contains("linkText"))
		{
			getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locator)));
		}
		else if(type.contains("partialLinkText"))
		{
			getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locator)));
		}
		else 
		{
			LoggerUtilities.addInfoLog("The object description locator type is invalid: "+ type);
		}
	}

	public static void waitForWindows(final int count) 
	{
		getWebDriverWait().until(ExpectedConditions.numberOfWindowsToBe(count));
	}

	public static void waitForElementText(final WebElement element, final String message) 
	{
		getWebDriverWait().until(ExpectedConditions.textToBePresentInElement(element, message));
	}

}