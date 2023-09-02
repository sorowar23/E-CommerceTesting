package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class TestListener implements ITestListener
{
	private static final Logger LOG = LoggerFactory.getLogger(TestListener.class);
	private static String BASELOCATION;
	private static String HOSTNAME;
	private static String REPORT_TITLE;
	private static String REPORT_NAME;
	
	public static String getBaseLocation() 
	{
		return BASELOCATION;
	}
	public static void setBaseLocation(final String baselocation) 
	{
		TestListener.BASELOCATION = baselocation;
	}
	public static String getHostname() 
	{
		return HOSTNAME;
	}
	public static void setHostname(final String hostname) 
	{
		TestListener.HOSTNAME = hostname;
	}
	public static String getReportTitle() 
	{
		return REPORT_TITLE;
	}
	public static void setReportTitle(final String reportTitle) 
	{
		TestListener.REPORT_TITLE = reportTitle;
	}
	public static String getReportName() 
	{
		return REPORT_NAME;
	}
	public static void setReportName(final String reportName) 
	{
		TestListener.REPORT_NAME = reportName;
	}
	
	/*
	 * Reading the Report location from Properties file
	 */
	
	public static void readReportLocation() 
	{
		InputStream input = null;
		try 
		{
			if(new File("config.properties").isFile())
			{
				input = Files.newInputStream(Paths.get("config.properties"));
			} 
			else
			{
				final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				input = classLoader.getResourceAsStream("config.properties");
			}
			final Properties prop = new Properties();
			prop.load(input);
			BASELOCATION = prop.getProperty("extentReportLocation");
			HOSTNAME = prop.getProperty("extentReport.hostname");
			REPORT_TITLE = prop.getProperty("extentReport.title");
			REPORT_NAME = prop.getProperty("extentReport.reportName");
			input.close();
		}
		catch (final IOException ex) 
		{
			LoggerUtilities.addLog(ex, "TestListener.readReportLocation()");
		}
		finally 
		{
			if(input !=null)
			{
				try 
				{
					input.close();
				}
				catch (final IOException e) 
				{
					LoggerUtilities.addLog(e, "TestListener.readReportLocation() -- finally block");
				}
			}
		}
	}
	
	/*
	 * Creating HTMLReporter for ExtentReport
	 */
	
	private ExtentSparkReporter htmlReporter;
	
	private ExtentReports extentReport;
	
	private static ExtentTest EXTENT_TEST;
	
	@Override
	public void onStart(final ITestContext context)
	{
		readReportLocation();
		extentReport = new ExtentReports();
		htmlReporter = new ExtentSparkReporter(BASELOCATION +"/" + TestListener.REPORT_TITLE+ "_" + DateTime.now().toString("yyyy-MM-dd-HH-mm-ss")+".html");
		extentReport.attachReporter(htmlReporter);
		extentReport.setSystemInfo("Hostname", "HOSTNAME");
		htmlReporter.config().setDocumentTitle(REPORT_TITLE);
		htmlReporter.config().setReportName(REPORT_NAME);
		htmlReporter.config().setTheme(Theme.DARK);
	}
	@Override
	public void onTestStart(final ITestResult result) 
	{
		TestListener.EXTENT_TEST = extentReport.createTest(result.getName());
		LOG.info("Starting of test -->{}", result.getMethod().getMethodName());
		SoftAssertionUtilities.instantiateSoftAssert();
	}
	
	@Override
	public void onTestSuccess(final ITestResult result) 
	{
		EXTENT_TEST.log(Status.PASS, "Test case passed is "+result.getName());
		LOG.info("Starting of test -->{}", result.getMethod().getMethodName());
		//organizeTestCaseValues(result, true);
	}
	@Override
	public void onTestFailure(final ITestResult result) 
	{
		try {
		
		EXTENT_TEST.log(Status.FAIL, "Test case failes is "+result.getName());
		EXTENT_TEST.log(Status.FAIL, "Test case failes due to "+result.getThrowable());
		
		if(UIUtilities.getDriver() != null) 
		{
			final String path = UIUtilities.takeScreenShotAtEndOfTest(UIUtilities.getDriver(), result.getName());
			LOG.info("ScreenShoot Path -->{}", path);
			EXTENT_TEST.fail("Screenshot"+ "\n", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		
		LOG.info("Starting of test -->{}", result.getMethod().getMethodName());
		LOG.info("Starting of test due to -->{}", result.getThrowable());
		
		//organizeTestCaseValues(result, true);
		}
		catch(final IOException ex){
			LoggerUtilities.addLog(ex, "TestListener.onTestFailure()");
		}
	}
	@Override
	public void onTestSkipped(final ITestResult result)
	{
		// TO-DO
	}
	@Override
	public void onTestFailedButWithinSuccessPercentage(final ITestResult result)
	{
		// TO-DO
	}
	
	@Override
	public void onFinish(final ITestContext context) {
		extentReport.flush();
		extentReport = null;
		
	}
	
	public static void extentTestLogInfo(final String info)
	{
		if(EXTENT_TEST !=null)
		{
			EXTENT_TEST.log(Status.INFO, info);
		}
	}
	
	public static void extentTestLogMarkUpInfo(final String info)
	{
		if(EXTENT_TEST !=null)
		{
			EXTENT_TEST.info(MarkupHelper.createCodeBlock(info));
		}
	}
	

}
