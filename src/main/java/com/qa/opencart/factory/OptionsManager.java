package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.qa.opencart.logger.Log;

public class OptionsManager {
	
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	private Properties prop;
	
	public OptionsManager(Properties prop) {
		this.prop= prop;
	}

	public ChromeOptions getChromeOption() {
		co = new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "chrome");
		}
		if(Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			//System.out.println("Running chrome in headless mode");
			Log.info("Running chrome in headless mode");
			co.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			co.addArguments("--incognito");
		}
		return co;
	}
	
	public FirefoxOptions getFirefoxOption() {
		fo = new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "firefox");
		}
		if(Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			//System.out.println("Running firefox in headless mode");
			Log.info("Running firefox in headless mode");
			fo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			fo.addArguments("--private");
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOption() {
		eo = new EdgeOptions();
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "edge");
		}
		if(Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("Running edge in headless mode");
			eo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			eo.addArguments("--inprivate");
		}
		return eo;
	}
}
