package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegisteration extends Baseclass {
	
	
	public WebDriver driver;
	
	public  AccountRegisteration(WebDriver driver){
		
		super(driver);
	}
	
	
	@FindBy(css="#input-firstname")
	WebElement txtFirstname;
	
	@FindBy(css="#input-lastname")
	WebElement txtlastname;
	
	@FindBy(css="#input-email")
	WebElement textemail;
	

	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txttelephone;
	
	
	@FindBy(css="#input-password")
	WebElement txtpsw;
	
	@FindBy(css="#input-confirm")
	WebElement txtconfirmpsw;
	
	@FindBy(xpath="//label[normalize-space()='Yes']")
	WebElement yesRadio;
	
	@FindBy(css="input[name='agree']")
	WebElement agreechck;
	
	@FindBy(css="input[value='Continue']")
	WebElement btncontinue;
	
	@FindBy(xpath="//h1[text()='Your Account Has Been Created!']")
	WebElement registermsg;
	
	
public void setFirstname(String firstname) {
	
	txtFirstname.sendKeys(firstname);
}


public void setlastname(String lastname) {

	txtlastname.sendKeys(lastname);
	
}


public void setEmail(String Email) {
	
	textemail.sendKeys(Email);
}


public void settelephone(String Phone) {

	txttelephone.sendKeys(Phone);
}


public void setpsw(String Password) {

	txtpsw.sendKeys(Password);
}


public void setconfirmpsw(String samepsw) {

	
	txtconfirmpsw.sendKeys(samepsw);
}

public void clickcheckbox() {
	
	agreechck.click();
}

public void clickyes() {

	yesRadio.click();
}

public void clickcontinue() {
	
	btncontinue.click();
}
	
	public String confirmmessage() {
			String text=registermsg.getText();
			return text;
		
	}
	

	
	
	
	
}
