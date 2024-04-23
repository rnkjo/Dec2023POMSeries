package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtils;

public class ProductInfoPage {

	
	private WebDriver driver;
	private ElementUtils eleUtil;
	private Map<String,String> productMap = new HashMap<String,String>();

	
	protected By pageHeader = By.tagName("h1");
	protected By images = By.cssSelector("ul.thumbnails img");
	protected By productMetaData= By.xpath("//div[@id='content']//ul[@class='list-unstyled'][1]/li");
	protected By productPriceData= By.xpath("//div[@id='content']//ul[@class='list-unstyled'][2]/li");
	protected By quantity = By.name("quantity");
	protected By addtoCart= By.id("button-cart");
	protected By alertMsg= By.xpath("//div[@class='alert alert-success alert-dismissible']");
	
	
	
	public ProductInfoPage(WebDriver driver) {
		this.driver= driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public String getProductHeader() {
		String header = eleUtil.getElement(pageHeader).getText();
		System.out.println("Product Header: " + header);
		return header;
	}

	public int getImageCount() {
		int countImages = eleUtil.waitForElementsVisible(images, 5).size();
		System.out.println("Count for product images : " +countImages);
		return countImages;
	}
	
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock
	
	public void getProductMetaData() {
		List<WebElement> metaData = eleUtil.getListWebElements(productMetaData);
		for(WebElement e : metaData) {
			String text= e.getText();
			String metaKey=text.split(":")[0].trim();
			String metaValue=text.split(":")[1].trim();
			productMap.put(metaKey, metaValue);
		}
		
	}
//	$2,000.00
//	Ex Tax: $2,000.00
	public void getProductPriceData() {
		List<WebElement> priceData = eleUtil.getListWebElements(productPriceData);
		String price = priceData.get(0).getText();
		String exTax= priceData.get(1).getText().split(":")[1].trim();
		productMap.put("productPrice", price);
		productMap.put("EX Tax", exTax);
	}
	
	
	public Map<String, String> getProductDetailMap() {
		productMap.put("productHeader", getProductHeader());
		productMap.put("productImages", String.valueOf(getImageCount()));
		getProductMetaData();
		getProductPriceData();
		return productMap;
	}
	
	public String addToCart(int qty) {
		eleUtil.doSendkeys(quantity, String.valueOf(qty));
		eleUtil.doClick(addtoCart);
		return eleUtil.waitForElementVisible(alertMsg, 5).getText();
	}
	
	
	
	
}
