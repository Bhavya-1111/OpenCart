package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.Homepage;
import pageObjects.Loginpage;
import pageObjects.Myaccountpage;

public class TC_004Logintest extends Basepage{
	
	
	@Test(groups = {"sanity","master"})
	public void verifyLogin() {
		
		try {
		
		Homepage hp=new Homepage(driver);
		
		hp.clickaccount();
		hp.clickLogin();
		System.out.println("Login clikced");
	
		
		Loginpage lp=new Loginpage(driver);
		lp.Enteruser(p.getProperty("username"));
		System.out.println("username Entered");
		
		lp.Enterpsw("password");
		System.out.println("password Entered");
		lp.clicklogin();
		System.out.println("Login button  clicked");
		
		
	Myaccountpage mc=new Myaccountpage(driver);
		
	boolean targetpage=mc.verifypage();
	
	Assert.assertTrue(targetpage);
	
		}
		catch(Exception e) {
			
			Assert.fail();
		}
		
	}

}
