package pageMethods;

import java.io.IOException;

import com.aventstack.extentreports.Status;

import pageObjects.LandingPageObjects;
import pageObjects.LoginPageObjects;

public class LandingPage extends basePage {
     
	
    LandingPageObjects landingPageObj= new LandingPageObjects(driver);
	LoginPageObjects loginPageObj= new LoginPageObjects(driver);
	
	//Function : Navigation to login page from landing page
	public void navigateToLoginPage() throws InterruptedException, IOException{
		common.click(landingPageObj.toggleMenu, "Toggle Menu");
		Thread.sleep(1000);
		common.clickUsingJavaScriptExecutor(landingPageObj.loginMenu,"Login Menu");
        common.waitUntilVisibilityOfElement(loginPageObj.username, 60);	
        logger.log(Status.PASS, "User navigated to login page successfully");
        logger.addScreenCaptureFromPath(common.CaptureScreenShot(driver, "login Success"));
	}

}
