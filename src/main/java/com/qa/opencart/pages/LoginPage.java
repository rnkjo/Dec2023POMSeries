package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtils;

public class LoginPage {

	//Page class/Page Library/Page Object
	private WebDriver driver;
	private ElementUtils eleUtil;
	// 1. Private By Locators
	private By email =By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn= By.cssSelector("input[value='Login']");
	private By forgotpwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");

	// 2. Public Page Class Const...
	public LoginPage(WebDriver driver) {
		this.driver= driver;
		eleUtil = new ElementUtils(driver);
	}
	
	//3.Public page Action/Method
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITILE, 5);
		Log.info("Login Page Title:" + title);
		return title;
	}
	
	public String getLoginPageUrl() {
		String url = eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_URL, 5);
		return url;
	}
	
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isDisplayed(forgotpwdLink);
	}
	
	public AccountPage doLogin(String username, String pwd) {
		eleUtil.waitForElementVisible(email, 5).sendKeys(username);
		eleUtil.doSendkeys(password, pwd);
		eleUtil.doClick(loginBtn);
		//return eleUtil.waitForTitleIs("My Account", 5);
		return new AccountPage(driver);
	}
	
	public RegistrationPage goToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegistrationPage(driver);
	}
	
}
