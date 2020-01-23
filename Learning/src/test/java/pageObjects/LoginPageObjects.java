package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pageMethods.basePage;

public class LoginPageObjects {
	
	
	@FindBy(xpath="//input[@id='txt-username']")
	public WebElement username;
	
	@FindBy(xpath="//input[@id='txt-password']")
	public WebElement password;
	
	@FindBy(xpath="//button[@id='btn-login']")
	public WebElement loginBtn;
	
	@FindBy(xpath="//a[contains(text(),'Logout')]")
	public WebElement logoutBtn;
	
	@FindBy(xpath="//section[@id='appointment']/div/div/div/h2")
	public WebElement dashboardMenu;
	
	@FindBy(xpath="//*[contains(text(),'Login failed')]")
	public WebElement loginErrorMsg;
	
	
	public LoginPageObjects(WebDriver driver){
		PageFactory.initElements(driver, this);
	  
  }

}
