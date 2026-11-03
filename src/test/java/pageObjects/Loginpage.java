package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Loginpage extends Baseclass{
	
	
	public WebDriver driver;
	
	public Loginpage(WebDriver driver){
		
		super(driver);
	}
	
	
	@FindBy(css="#input-email")
	WebElement txtusername;
	
	@FindBy(css="#input-password")
	WebElement txtpassword;
	
	@FindBy(css="input[value='Login']")
	WebElement btnlogin;
	
	
	
	public void Enteruser(String uname) {
		
		txtusername.sendKeys(uname);
	}
	
	public void Enterpsw(String psw) {
		
		txtpassword.sendKeys(psw);
		
	}
	
	public void clicklogin() {
		
		btnlogin.click();
	}
	
	
		
	}
	
	
	

