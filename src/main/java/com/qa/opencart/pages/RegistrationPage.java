package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtils;

public class RegistrationPage {
	private WebDriver driver;
	private ElementUtils eleUtil;
	private By fName = By.id("input-firstname");
	private By lName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone= By.id("input-telephone");
	private By pwd = By.id("input-password");
	private By conpwd= By.id("input-confirm");
	private By policyChkBox = By.name("agree");
	private By subscribeYes=By.xpath("//input[@name='newsletter' and @value='1']");
	private By subscribeNo=By.xpath("//input[@name='newsletter' and @value='0']");
	private By contBtn = By.xpath("//input[@type=\"submit\"]");
	private By sucessMessg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	
	public RegistrationPage(WebDriver driver) {
		this.driver= driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public String userRegister(String firstName,String lastName,String email,String telephone,String password,String subscribe) {
		eleUtil.waitForElementVisible(this.fName, 5).sendKeys(firstName);
		eleUtil.doSendkeys(this.lName, lastName);
		eleUtil.doSendkeys(this.email, email);
		eleUtil.doSendkeys(this.telephone, telephone);
		eleUtil.doSendkeys(this.pwd, password);
		eleUtil.doSendkeys(this.conpwd, password);		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}else {
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doClick(policyChkBox);
		eleUtil.doClick(contBtn);
		
		String successMsg= eleUtil.waitForElementVisible(sucessMessg, 10).getText();
		System.out.println(successMsg);
		eleUtil.doClick(logoutLink);
		eleUtil.doClick(registerLink);
		
		return successMsg;
	}
	
	
	
	

}
