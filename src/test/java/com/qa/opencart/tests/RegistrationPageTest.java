package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegistrationPageTest extends BaseTest{
	
	
	@BeforeClass
	public void registerPageSetup() {
		 registerPage = loginPage.goToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getUserDataExcel() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEETNAME);
	}

	@Test(dataProvider = "getUserDataExcel")
	public void userRegTest(String firstname,String lastName,String telephone,String password,String subcribe) {
		String msg = registerPage.userRegister(firstname, lastName, StringUtils.randomEmail(), telephone, password, subcribe);
		Assert.assertEquals(msg, AppConstants.SUCCESS_REG_MSG);
	}
}
