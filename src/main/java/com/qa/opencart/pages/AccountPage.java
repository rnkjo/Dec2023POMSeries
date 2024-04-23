package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class AccountPage {

	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By myAccountLink = By.linkText("My Account");
	private By headerList = By.xpath("//h2"); // cssSelector: div#content h2
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	public AccountPage(WebDriver driver) {
		this.driver= driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public String getAccountPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.MYACCOUNT_PAGE_TITILE, 5);
		System.out.println("Account Page Title:" +title);
		return title;
	}
	
	public String getAccountPageUrl() {
		String url = eleUtil.waitForUrlContains(AppConstants.MYACCOUNT_PAGE_URL, 5);
		System.out.println("Account Page URL:" +url);

		return url;
	}
	
	public boolean isMyAccountLinkExist() {
		return eleUtil.isDisplayed(myAccountLink);
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, 5).isDisplayed();
	}
	
	public List<String> getHeaderList() {
		List<WebElement> headerEleList = eleUtil.getListWebElements(headerList);
		List<String> headersList = new ArrayList<String>();
		for(WebElement e:headerEleList ) {
			String text = e.getText();
			headersList.add(text);
		}
		return headersList;
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("Search Key : " + searchKey);
		eleUtil.doSendkeys(search, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}
	
}
