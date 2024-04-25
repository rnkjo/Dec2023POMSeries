package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exeception.FrameworkException;
import com.qa.opencart.exeception.BrowserException;
import com.qa.opencart.logger.Log;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tldriver= new ThreadLocal<WebDriver>();
	public static String highlight;
	
	public WebDriver initDriver(Properties prop) {
		
		String browserName =prop.getProperty("browser");
		Log.info("Browser Name is: " +browserName);
		String url = prop.getProperty("url");
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop); 
		
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			tldriver.set(new ChromeDriver(optionsManager.getChromeOption()));
			break;
		case "firefox":
			//driver =new FirefoxDriver(optionsManager.getFirefoxOption());
			tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOption()));
			break;
		case "edge":
			driver =new EdgeDriver(optionsManager.getEdgeOption());
			tldriver.set(new EdgeDriver(optionsManager.getEdgeOption()));
			break;
		case "safari":
			//driver =new SafariDriver();
			tldriver.set(new SafariDriver());
			break;
		default:
			//System.out.println("Please pass the proeper browser name.....");
			Log.error("Please pass the proeper browser name....." +browserName);
			throw new BrowserException("No Browser Found.....");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(url);
		
		return getDriver();
	}
	
	public static WebDriver getDriver() {
		return tldriver.get();
	}
	
	public Properties initProp() {
		//mvn clean install -Denv="qa"
		FileInputStream ip=null;
		prop= new Properties();
		String envName= System.getProperty("env");
		Log.info("Running tests on " +envName);
		
		try {
			
		
		if(envName == null) {
			 ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
		}else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				 ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
				break;
			case "uat":
				 ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
				break;
			case "dev":
				 ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
				break;
			case "stage":
				 ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
				break;
			case "prod":
				 ip = new FileInputStream("./src/test/resources/config/config.properties");
				break;
			default:
				Log.error("Please Pass the right env name....");
				throw new FrameworkException(envName);
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		}  catch (IOException e) {
			e.printStackTrace();
		}
	
		return prop;
		
	}
	
	public static String getScreenshot(String methodName) {
		File srcFile= ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+methodName+"_"+ System.currentTimeMillis()+".png";
		
		File destination =new File(path);
		
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
				
	}

}
