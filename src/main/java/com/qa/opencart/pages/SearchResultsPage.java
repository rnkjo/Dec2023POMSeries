package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtils;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private By searchProducts = By.cssSelector("div.product-thumb");
	
	
	public SearchResultsPage(WebDriver driver) {
		this.driver= driver;
		eleUtil = new ElementUtils(driver);
	}

	public int getSearchCountProduct() {
		return eleUtil.waitForElementsVisible(searchProducts, 5).size();
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Select Product : "+ productName);
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
		
	}
	
}
