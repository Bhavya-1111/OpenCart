package testCases;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Basepage {
	
	
	public WebDriver driver;
	public Logger logger;
	public Properties p;
	
	
	@BeforeClass
	void browserlaunch() throws IOException {
		
	 driver=new ChromeDriver();
	 
	 logger=LogManager.getLogger(this.getClass());
	
		 driver.manage().window().maximize();
		 
		FileReader file = new FileReader(System.getProperty("user.dir") + "//src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		 
		 driver.get(p.getProperty("appurl"));
		 
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
		
	}
	
	
	@AfterClass
	void closebrowser() {
		
		if (driver != null) {
            driver.quit();
        }
	}
	
	
	
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		File dir = new File(System.getProperty("user.dir") + "/screenshots");
		dir.mkdirs();

		String targetFilePath = dir.getAbsolutePath() + "/" + tname + "_" + timeStamp + ".png";
		sourceFile.renameTo(new File(targetFilePath));

		return targetFilePath;
	}

}
