package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Homepage extends Baseclass{
	
	public WebDriver driver;
	
	public Homepage(WebDriver driver){
		
		
		super(driver);
		
	
	}
	
	@FindBy(xpath="//span[text()='My Account']")
	WebElement Myaccountlnk;
	
	@FindBy(xpath="//a[text()='Register']")
	WebElement Registerlnk;
	
	@FindBy(xpath="//a[text()='Login']")
	WebElement btnLogin;
	
	
	
	
	
	
	
	
	/*
	// without pagefactory---POM 
	By Registerlnk=By.xpath("//a[text()='Register']");
	
	By Myaccountlnk =By.xpath("//span[text()='My Account']");
	*/
	
	public void clickaccount() {
		
		Myaccountlnk.click();
	}

public void clickregister() {
		
	Registerlnk.click();
		
	}


public void clickLogin() {
	btnLogin.click();
	
}



}
