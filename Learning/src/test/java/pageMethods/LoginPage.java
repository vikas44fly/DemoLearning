package pageMethods;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import pageObjects.LoginPageObjects;

public class LoginPage extends basePage{
	
	LoginPageObjects loginpageObj=new LoginPageObjects(driver);
	
	
	public void enterCredentials(String username,String password){		
		common.enterText(loginpageObj.username, username, "Username InputBox");
		common.enterText(loginpageObj.password, password, "Password InputBox");
				
	}
	
	public void clickLoginBtn(){	
		common.click(loginpageObj.loginBtn, "Login button");
	}
	
	public void validateLoginErrorMag(){	
		boolean visible=common.isElementDisplayed(loginpageObj.loginErrorMsg);
		Assert.assertTrue(visible, "Error message not appeared");
		logger.log(Status.PASS, "Error message appeared for invalid login");
	}

}
