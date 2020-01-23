package utilities;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.google.common.base.Function;
import pageMethods.basePage;

public class Common extends basePage{
	
	public WebElement element = null;
	private WebElement dropdown = null;
	private Select selectList = null;
	public SoftAssert softAssert=new SoftAssert();
	public int maxTime=60;
	
	
	public String CaptureScreenShotBase64(WebDriver driver,  String screenName )  {
  
        SimpleDateFormat df= new SimpleDateFormat("hh_mm_ss_a");
         Date date= new Date();
         String base64String = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
         return "data:image/png;base64,"+base64String;

}
	
	public String CaptureScreenShot(WebDriver driver, String screenName){             
        TakesScreenshot efwd=((TakesScreenshot) driver);
	    File srcFile=efwd.getScreenshotAs(OutputType.FILE);   
	    String fileDest= userDir + "\\ScreenShots\\" + screenName + getTimeStamp() + ".png";
	    try {
              FileUtils.copyFile(srcFile, new File(fileDest));       
          } catch (Exception e) {        
              e.printStackTrace();
         }
         return fileDest;         
  }
	
	public String getTimeStamp(){                 
		   DateFormat dateFormat=new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
		   Date date=new Date();
		   String DateTime=dateFormat.format(date);       
		return DateTime.toString();       
		}
	
	public WebElement fluentWait(final WebElement webby) {
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(180))
					.pollingEvery(Duration.ofMillis(600)).ignoring(NoSuchElementException.class);
			WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					if (webby.isDisplayed())
						return webby;
					return webby;
				}
			});
			return foo;
		} catch (NoSuchElementException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement waitForElementExists(WebElement element, int timoutSec, int pollingMiliSec) {

		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timoutSec))
				.pollingEvery(Duration.ofMillis(pollingMiliSec)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		for (int i = 0; i < 2; i++) {
			try {
				fWait.until(ExpectedConditions.visibilityOf(element));
				fWait.until(ExpectedConditions.elementToBeClickable(element));
			} catch (Exception e) {
				System.out.println("Element Not found trying again - " + element.toString().substring(70));
				e.printStackTrace();

			}
		}
		return element;
	}

	public boolean isElementDisplayed(WebElement webby) {
		element = fluentWait(webby);
		return element.isDisplayed();
	}
	
	public boolean isElementsDisplayed(List<WebElement> webby) {
		if(webby.size()>0)
		{
			return true;
		}
		else
			return false;
	}
	
	public void wait(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < n);
	}
	
	public String getElementText(WebElement webby) {
		element = fluentWait(webby);
		return element.getText();
	}

	public void scrollToElement(WebElement webby) {
		element = fluentWait(webby);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView();", new Object[] { element });
	}

	public WebElement explicitWait(WebElement webby, int waittime) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, waittime);
		WebElement element = (WebElement) wait.until(ExpectedConditions.visibilityOf(webby));
		wait(500);
		return element;
	}
	
	
	public boolean waitUntilInVisibilityOfElement(WebElement webby , int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		Boolean bool = (Boolean) wait.until(ExpectedConditions.invisibilityOf(webby));
		return bool;
	}

	public String getPageTitle() {
		String pageTitle = driver.getTitle();
		logger.info("Page title: " + pageTitle);
		return pageTitle;
	}

	public boolean isElementEnabled(WebElement webby) {
		element = fluentWait(webby);
		return element.isEnabled();
	}


	public void click(WebElement webby,String btn) {
		waitUntilElementClickable(webby, maxTime);
		webby.click();
		logger.log(Status.INFO, "Clicked on "+btn);
	}
	
	public void enterText(WebElement webby,String Text,String elementName){
		waitUntilVisibilityOfElement(webby, maxTime);
		webby.sendKeys(Text);
		logger.log(Status.INFO, "User entered text as "+Text+" on "+elementName);
	}
	
	public void clickUsingJavaScriptExecutor(WebElement webby,String Msg) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webby);
		logger.log(Status.INFO, "Clicked on "+"Clicked on "+ Msg);
	}
	
	public void selectOptionFromDropdown(WebElement webby, String optionBy, String option) {
		dropdown = fluentWait(webby);
		selectList = new Select(dropdown);

		if (optionBy.equals("selectByIndex"))
			selectList.selectByIndex(Integer.parseInt(option) - 1);
		else if (optionBy.equals("value"))
			selectList.selectByValue(option);
		else if (optionBy.equals("text"))
			selectList.selectByVisibleText(option);
	}

	public static boolean isClickable(WebDriver driver, WebElement el, int time) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void waitUntilVisibilityOfElement(WebElement webby , int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver,waitTime);
		wait.until(ExpectedConditions.visibilityOf(webby));
	}
	
	
	public boolean is_dd_empty(WebElement element) {
    	Select sel = new Select(element);
    	List <WebElement> list = sel.getOptions();
    	if(list.isEmpty()) {
           System.out.println("Drop down does not have any option!");
    		return false;
    	}else {
    		return true;
    	 }}
	
	public void waitUntilElementClickable(WebElement webby , int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver,waitTime);
		wait.until(ExpectedConditions.elementToBeClickable(webby));
	}
	
	public void waitUntilVisibilityOfElement(List<WebElement> webby , int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver,waitTime);
		wait.until(ExpectedConditions.visibilityOfAllElements(webby));
	}
	
	
	/**
	* Generic method to retrieve System Operating System
	*/
	public static String getSystemOperatingSystem() {
	return System.getProperty("os.name");
	}

	/**
	* Generic method to retrieve User Working Directory
	*/
	public static String getUserWorkingDirectory() {
	return System.getProperty("user.dir");
	}

	/**
	* Generic method to retrieve User Home Directory
	*/
	public static String getUserHomeDirectory() {
	return System.getProperty("user.home");
	}
	//Method to check the list is in ascending order
		public static <T extends Comparable<T>> boolean isAscendingSorted(List<T> listOfT) {
	 	T previous = null;
		for (T t : listOfT) {
		if ((previous != null) && (t.compareTo(previous) < 0)) {
		return false;
	 	}
		previous = t;
		}
		return true;
		}
	 	//Method to check the list is in descending order
		public static <T extends Comparable<T>> boolean isDescendingSorted(List<T> listOfT) {
		T previous = null;
		for (T t : listOfT) {
		if ((previous != null) && (t.compareTo(previous) > 0)) {
		return false;
		}
		previous = t;
		}
		return true;
		}
		
		// Method to read the selected option in drop down
		public String readSelectedDropdownElement(WebElement element) {
			Select select = new Select(element);
			WebElement dropdown = select.getFirstSelectedOption();
			return dropdown.getText();
		}
		
		
	
		
		//Method to select by text from drop down
		public void selectTheDropDownList(WebElement dropDown, String text) {
			Select select = new Select(dropDown);
			select.selectByVisibleText(text);		 
		}
		
		public String getCurrentAppUrl() {
			return driver.getCurrentUrl();		 
		}
		
		public void launchUrlInBrowser(String url) {
			driver.get(url);		 
		}
                public  boolean isElementHidden(WebElement el){
		 try
		  {
			el.click();
			return false;
		  }
		catch(Exception e)
		  {
			return true;
		  }
	        }
	
	public void waitUntilElementActive(WebDriver driver, int waittime) throws InterruptedException {
		int timeToLoad=waittime*1000;
		wait(timeToLoad);
	}
	
	public boolean validateElementAvailability(WebElement Element)
    {
     if(Element.isDisplayed()==true){
      return true;
    }
    else
      return false;
    }
	
	
}

