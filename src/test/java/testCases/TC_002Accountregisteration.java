package testCases;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegisteration;
import pageObjects.Homepage;

public class TC_002Accountregisteration extends Basepage{
	
	@Test
	void Homepagetest() {
		
		try {
		logger.info("*******Test case Execution Started*****");
		
		Homepage hp=new Homepage(driver);
		
		hp.clickaccount();
		
		logger.info("*******Clicked on Myaccountlink******");
		
		hp.clickregister();
		
		logger.info("*******Clicked on Register Link******");
		
		AccountRegisteration ar=new AccountRegisteration(driver);
		
		ar.setFirstname("Bhavya");
		ar.setlastname("Rani") ;
		ar.setEmail("Bhavya5678@gmail.com");
		ar.settelephone("123456789");
		ar.setpsw("Bhavi1234!");
		ar.setconfirmpsw("Bhavi1234!");
		
		ar.clickcheckbox();
		ar.clickyes();
		ar.clickcontinue();
		
		logger.info("****All the Input entered***");
		
	String message=ar.confirmmessage();
	
	if(message.equals("Your Account Has Been Created!")) {
		
		
		logger.info("Test logger info status");
		logger.debug(" Test debug logger");
		
		Assert.assertTrue(true);
		
		
		
	}else {
		
		logger.error("Test failed");
		logger.debug("Debug logs");
		Assert.assertTrue(false);
	}
		}
		catch(Exception e) {
			
			logger.error("Test Failed with Exception" +  e.getMessage());
			Assert.fail("Test Failed" + e.getMessage());
		
		}
		
	}
	
	


}
