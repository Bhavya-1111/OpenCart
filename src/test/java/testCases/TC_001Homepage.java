package testCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import pageObjects.AccountRegisteration;
import pageObjects.Homepage;

public class TC_001Homepage  extends Basepage{

	
	
	@Test
	void Hoempagetest() {
		
		logger.info("***Test Execution Started");
		
		Homepage hp=new Homepage(driver);
		hp.clickaccount();
		logger.warn("Give Proper Value");
		logger.info("***Account clicked ***");
		hp.clickregister();
		logger.info("***Register clicked ***");
		logger.debug("Clicked" + driver.getCurrentUrl());
		
		
		
	}
	
	
	
	
}
