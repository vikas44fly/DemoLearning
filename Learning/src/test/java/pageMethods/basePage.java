package pageMethods;

	import java.awt.Robot;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.text.DateFormat;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.util.HashMap;
	import java.util.Map;
	import java.util.Properties;
	import java.util.concurrent.TimeUnit;

	import org.apache.poi.ss.usermodel.DataFormatter;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.Platform;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.firefox.FirefoxProfile;
	import org.openqa.selenium.ie.InternetExplorerDriver;
	import org.openqa.selenium.remote.CapabilityType;
	import org.openqa.selenium.remote.DesiredCapabilities;
	import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.ITestResult;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.AfterSuite;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.BeforeSuite;
	import org.testng.annotations.BeforeTest;
	import org.testng.asserts.SoftAssert;
	import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
    import com.aventstack.extentreports.ExtentReports;
	import com.aventstack.extentreports.ExtentTest;
	import com.aventstack.extentreports.Status;
	import com.aventstack.extentreports.markuputils.ExtentColor;
	import com.aventstack.extentreports.markuputils.MarkupHelper;
	import cucumber.api.java.Before;
import utilities.Common;
import utilities.TestDataManager;

import java.lang.reflect.Method;
	
	public class basePage {

		
		public static WebDriver driver = null;
		public static String userDir=System.getProperty("user.dir");
		public static DesiredCapabilities cap =null;
		public static Properties prop= new Properties();
		public  Map<String, String> map= new HashMap<String, String>();
		public static ExtentReports report;
		public static ExtentTest logger;
		public static ExtentHtmlReporter htmlReporter;
		
		public static WebDriverWait wait;
		public static SoftAssert assertion=new SoftAssert();
		public static String filePath;
		public static FileInputStream fn=null;
		public static String browser=null;
		public DataFormatter formatter = new DataFormatter();
		DateFormat dFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
		
		public static  Common common=new Common();
		public static basePage bp=new basePage();
		public static TestDataManager testdata=new TestDataManager();
		
	
		
		@BeforeSuite
		public void setup() throws IOException{
			basePage.loadPropertiesFile();
			basePage.launchDriver(browser);
			
		}
		
		@BeforeClass
		public void startExtentReport() throws IOException{
			String className=this.getClass().getSimpleName();
			bp.launchExtentReport(browser,className);			
		}
		
		@BeforeMethod
		public void createExtentTest(Method method) throws InterruptedException{
			driver.manage().deleteAllCookies();
			logger=report.createTest(method.getName());
			Thread.sleep(2000);
			driver.navigate().to(prop.getProperty("url"));
			driver.manage().window().maximize();
		}
		
		@AfterMethod
		public void getResult(ITestResult result){
			if(result.getStatus() == ITestResult.FAILURE){
				logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
				logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
			}else if(result.getStatus() == ITestResult.SKIP){
				logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));	
			}
		}
		
		
		/**
	     *  Load Properties file 
	     * 
	     */
		public static void loadPropertiesFile() throws IOException
		{
			if(fn==null)
			{
			fn= new FileInputStream( userDir + "\\src\\test\\java\\configuration\\config.properties");		
			prop.load(fn);	
			System.out.println("Properties File Loaded Successfully");
			browser=prop.getProperty("browser");		
		}
		}
		
		/**
	     *  Extent report Initiation
	     * 
	     */
		
		public ExtentReports launchExtentReport(String browser,String className) throws IOException {  
		     System.out.println("Intiating Extent Report");  
			 String destFile=null;
			 destFile=userDir + "\\test-output\\extentreports\\learning";
			 DateFormat dateFormat=new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");                
			 String destDir=dateFormat.format(new Date()) + ".html";    
			 htmlReporter = new ExtentHtmlReporter(destFile + "/" + browser +"_"+className+"_"  + "_" + destDir); 
			 report = new ExtentReports();
			 report.attachReporter(htmlReporter);
			 report.setSystemInfo("Browser", browser);
			 report.setSystemInfo("OS", prop.getProperty("platform"));
			 htmlReporter.loadXMLConfig(new File(userDir + "/src/test/java/configuration/reports.xml"));
			 return report;       
			 
			 }
		
		

		/**
	     *  Launch Browser
		 * @throws IOException 
	     * 
	     */
		
		public static WebDriver launchDriver(String Browser) throws IOException
		{		
			
			switch (Browser) {
			case "Chrome":
				System.setProperty("webdriver.chrome.driver", userDir + "\\browserDrivers\\chromedriver.exe");
				cap = DesiredCapabilities.chrome();
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.WINDOWS);	
				driver=new ChromeDriver();
				break;
				
			case "Firefox":
				System.setProperty("webdriver.gecko.driver",userDir + "\\browserDrivers\\geckodriver.exe");
				cap = DesiredCapabilities.firefox();
				cap.setCapability("marionette", true);
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.WINDOWS);
				driver=new FirefoxDriver();
				break;
				
			case "IE":
				System.setProperty("webdriver.ie.driver",userDir + "\\browserDrivers\\IEDriverServer.exe");
				cap = DesiredCapabilities.internetExplorer();
				cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				cap.setPlatform(Platform.ANY);
				cap.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR,true);
				cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				cap.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);
				cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);
				cap.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
				cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS,false);
				driver=new InternetExplorerDriver();
				break;
			}
			System.out.println("Browser Launched Successfully "+Browser);
		       return driver;
				
			}
		
		
		
		/**
	     *  Terminate test logger and close browser
		 *   @throws IOException 
	     * 
	     */
		
		@AfterClass
		public void reportclosewindows(){
		   
		    report.flush();	
		    
		    } 
		
		@AfterSuite
		public void quitSetup(){
			//driver.quit();
		}
		
			
		}
				
	
