package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pageMethods.basePage;

public class HomePageObjects {
	
	@FindBy(xpath="//select[@id='combo_facility']")
	public WebElement facilityDropdown;
	
	@FindBy(xpath="//input[@id='radio_program_medicaid']")
	public WebElement programMedicaid;
	
	@FindBy(xpath="//input[@name='visit_date']")
	public WebElement visitDate;
	
	@FindBy(xpath="//textarea[@id='txt_comment']")
	public WebElement commentBox;
	
	@FindBy(xpath="//button[text()='Book Appointment']")
	public WebElement bookAppointmentBtn;
	
	@FindBy(xpath="//h2[contains(text(),'Appointment Confirmation')]")
	public WebElement confirmationMsg;
	
	
	public HomePageObjects(WebDriver driver){
		PageFactory.initElements(driver, this);
	  
  }

}
