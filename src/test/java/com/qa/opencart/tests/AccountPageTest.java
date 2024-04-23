package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountPageTest extends BaseTest{
	
	@BeforeClass
	public void accountSetup() {
		 accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accountPageTitleTest() {
		String actTitle = accPage.getAccountPageTitle();
		Assert.assertEquals(actTitle, AppConstants.MYACCOUNT_PAGE_TITILE);
	}
	
	@Test
	public void accountPageUrlTest() {
		String actUrl = accPage.getAccountPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.MYACCOUNT_PAGE_URL));//account 
	}
	
	@Test
	public void islogoutLinkTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void headerListTest() {
		List<String> headerList = accPage.getHeaderList();
		System.out.println(headerList);
	}
	@Test
	public void searchTest() {
		accPage.doSearch("macbook");
	}
	
}
