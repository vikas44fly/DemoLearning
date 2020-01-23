package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

import pageMethods.LandingPage;
import pageMethods.LoginPage;
import pageMethods.basePage;
import utilities.TestDataManager;


public class LoginTest extends basePage{
	
	
    
	@Test(testName="validate Login Functionality",priority=0)
	public void validateLoginFunctionality() throws InterruptedException, IOException{
		
		LandingPage lp=new LandingPage();
	    LoginPage lop=new LoginPage();
			    
		lp.navigateToLoginPage();
		String username=testdata.read_property("LoginPage", "loginpage", "username");
		String password=testdata.read_property("LoginPage", "loginpage", "password");
		
		lop.enterCredentials(username, password);
		lop.clickLoginBtn();
		
	}
	
	@Test(testName="validate Invalid Login Functionality",priority=1,enabled=true)
	public void validateInvalidLoginFunctionality() throws InterruptedException, IOException{
		
		LandingPage lp=new LandingPage();
	    LoginPage lop=new LoginPage();
			    
		lp.navigateToLoginPage();
		String username=testdata.read_property("LoginPage", "loginpage", "username");
		String password=testdata.read_property("LoginPage", "loginpage", "invalidPassword");
		
		lop.enterCredentials(username, password);
		lop.clickLoginBtn();
		lop.validateLoginErrorMag();
		
	}
}
