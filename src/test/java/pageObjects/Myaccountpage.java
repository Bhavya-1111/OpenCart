package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Myaccountpage extends Baseclass{
	
	public WebDriver driver;
	
	public 	Myaccountpage(WebDriver driver){
		
		super(driver);
		
	}
	
	@FindBy(xpath="//h2[normalize-space()='My Account']")
	WebElement logingheader;
	
	@FindBy(xpath="//span[text()='My Account']")
	WebElement Myaccount;
	
	@FindBy(xpath="//a[text()='Logout']")
	WebElement lnklogout; 
	
public boolean verifypage() {
		
		try {
		return (logingheader.isDisplayed());
		}
		catch(Exception e) {
			
			return false;
			
		}

}
public void clickmyaccount() {
	Myaccount.click();
}


public void clicklogout() {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	wait.until(ExpectedConditions.elementToBeClickable(lnklogout)).click();
}








}
