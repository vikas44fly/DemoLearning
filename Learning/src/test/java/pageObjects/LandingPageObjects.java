package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LandingPageObjects  {
	
	@FindBy(xpath="//a[@id='menu-toggle']/i")
	public WebElement toggleMenu;
	
	@FindBy(xpath="//a[contains(text(),'Login')]")
	public WebElement loginMenu;
	
	public LandingPageObjects(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

}
