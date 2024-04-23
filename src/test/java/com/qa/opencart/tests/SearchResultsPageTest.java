package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchResultsPageTest extends BaseTest{
	
	@BeforeClass
	public void accountSetup() {
		 accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void searchResultsCountTest() {
		searchResultsPage = accPage.doSearch("macbook");
		int count = searchResultsPage.getSearchCountProduct();
		Assert.assertEquals(count, 3);
	}
	
	

}
