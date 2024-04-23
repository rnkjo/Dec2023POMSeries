package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exeception.BrowserException;
import com.qa.opencart.factory.DriverFactory;


public class ElementUtils {
		
		private WebDriver driver;
		private JavaScriptUtil jsUtil;
		private String ELEMENT_TIME_OUT_MESSAGE="Time out... Element is not found...";
		private String ALERT_TIME_OUT_MESSAGE="Time out... Alert is not found...";
		
		
		public ElementUtils(WebDriver driver) {
			this.driver = driver;
			jsUtil=new JavaScriptUtil(driver);
		}
		
		public By getBy(String locatorType, String locatorValue) {
			
			 By locator = null;
			switch (locatorType) {
			case "id":
				locator = By.id(locatorValue);
				break;

			default:
				break;
			}
			return locator;
		}
		private void nullBlankCheck(String value) {
			if(value == null || value.length() == 0) {
				throw new BrowserException(value +"--Value can not be null");
			}
		}
		private void checkHighlight(WebElement element) {
			if(Boolean.parseBoolean(DriverFactory.highlight)) {
				 jsUtil.flash(element);
			 }
		}
		public WebElement getElement(By locator) {
			WebElement element = null;
			
			try {
				 element = driver.findElement(locator);
				 checkHighlight(element);
			} catch (NoSuchElementException e) {
				System.out.println("No such Element found..");
				e.printStackTrace();
			}
			 return element;
		}
		
		public WebElement getElement(String locatorType, String locatorValue) {
			 WebElement element= driver.findElement(getBy(locatorType, locatorValue));
			 checkHighlight(element);
			 return element;
		}
		
		public void doSendkeys(By locator, String value) {
			getElement(locator).clear();
			getElement(locator).sendKeys(value);
		}
		public void doSendkeys(String locatorType, String locatorValue, String value) {
			getElement(locatorType, locatorValue).sendKeys(value);
		}
		
		public By getLocatorbyId(String value) {
			return By.id(value);
		}
		
		public void doClick(By locator) {
			getElement(locator).click();
		}
		public String doGetElementText(By locator) {
			return getElement(locator).getText();
		}
		
		public String dogetElementAttribute(By locator,String value) {
			return getElement(locator).getAttribute(value);
		}

		public  List<WebElement> getListWebElements(By locator) {
			return driver.findElements(locator);
		}
		
		public  int getElementCount(By locator) {
			return getListWebElements(locator).size();
		}
		
		public  ArrayList<String> getElementListText(By locator) {
			List<WebElement> listEle= getListWebElements(locator);
			ArrayList<String> 	eletext = new ArrayList<String>();
			
			for(WebElement e:listEle) {
				String text = e.getText();
				if(text.length() != 0) {
					eletext.add(text);
				}
			}
			
			return eletext;
		}
		public  ArrayList<String> getAttributeListText(By locator,String attrName) {
			List<WebElement> listAttr= getListWebElements(locator);
			ArrayList<String> 	eleAttrlist = new ArrayList<String>();
			
			for(WebElement e:listAttr) {
				String text = e.getAttribute(attrName);
				if(text.length() != 0) {
					eleAttrlist.add(text);
				}
			}
			
			return eleAttrlist;
		}
		
		public Boolean isDisplayed(By locator) {
			return getElement(locator).isDisplayed();
		}
		
		public Boolean isElementExist(By locator) {
			if(getListWebElements(locator).size() == 1) {
				return true;
			}
			return false;
		}
		
		public Boolean multipleElementExist(By locator) {
			if(getListWebElements(locator).size() >= 1) {
				return true;
			}
			return false;
			
		}
		
	// ****************** Select based drop down ******************//
		public void doSelectByIndex(By locator,int index) {
			WebElement selectCountry = getElement(locator);
			Select sel= new Select(selectCountry);
			sel.selectByIndex(index);
		}

		public void doSelectByValue(By locator,String value) {
			nullBlankCheck(value);
			WebElement selectCountry = getElement(locator);
			Select sel= new Select(selectCountry);
			sel.selectByValue(value);
		}
		public void doSelectByVisibleText(By locator,String value) {
			nullBlankCheck(value);
			WebElement selectCountry = getElement(locator);
			Select sel= new Select(selectCountry);
			sel.selectByVisibleText(value);
		}
		
		public List<WebElement> getDropDownOptionList(By locator) {
			Select sel= new Select(getElement(locator));
			return sel.getOptions();
		}
		 
		public int getDropDownValueCounts(By locator) {
			return getDropDownOptionList(locator).size();
		}
		
		public List<String> getDropDownOptionsTextValue(By locator) {
			List<WebElement> selOptions = getDropDownOptionList(locator);	
			List<String> optionTextList = new ArrayList<String>();
			
			for(WebElement e:selOptions) {
				String country = e.getText();
				optionTextList.add(country);
			}
			return optionTextList;
		}
		public void doSelectDropdownByValue(By locator,String value) {
			List<WebElement> selOptions = getDropDownOptionList(locator);	
			//System.out.println(selOptions.size());
			for(WebElement e:selOptions) {
				String country = e.getText();
				if (country.equals(value)) {
					e.click();
					break;
				}
				//System.out.println(country);
			}

		}
		
		// Without select class
		
		public void DoSelectValueFromDropDown(By locator,String value) {
			List<WebElement> optionsList = getListWebElements(locator);	
			System.out.println(optionsList.size());
			for(WebElement e:optionsList) {
				String country = e.getText();
				if (country.equals(value)) {
					e.click();
					break;
				}
				//System.out.println(country);
			}

		}
		
		//************Auto Search suggestion- Google**************//
		
		public void doSearch(By locator, By searchLocator,String searchText, String searchValue) throws InterruptedException {
			
			doSendkeys(locator, searchText);
			Thread.sleep(3000);
			List<WebElement> searchOptions = getListWebElements(searchLocator);
			for(WebElement e: searchOptions) {
				String text = e.getText();
				System.out.println(text);
				if(text.contains(searchValue)) {
					e.click();
					break;
				}
			}
		}
		
		/**
		 * This method pass single, multiple, and all option..
		 * @param locator
		 * @param chkValue
		 */
		
		//... varargs --spread parameter(JS) -- array
		public void selectChoice(By locator,String... chkValue) {
			List<WebElement> findElements = getListWebElements(locator);
			System.out.println(chkValue.length);
			for(WebElement e :findElements ) {
				String text = e.getText();
				
				if(chkValue[0].equals("all")) {
					e.click();
				}else {
					for(String v:chkValue) {
						if(text.equals(v)) {
							e.click();
						}
					}	
				}						
			}
		}
		
		//***************Actions Class*****************//
		public void handleMultipleLevelMenu(By level1Ele,By level2Ele, By level3Ele, By level4Ele) throws InterruptedException {
			getElement(level1Ele).click();
			Thread.sleep(2000);

			Actions act = new Actions(driver);
			act.moveToElement(getElement(level2Ele)).perform();
			Thread.sleep(2000);

			act.moveToElement(getElement(level3Ele)).perform();
			Thread.sleep(2000);
			getElement(level4Ele).click();

		}
		
		public void doActionClick(By locator) {
			Actions act = new Actions(driver);
			act.click(getElement(locator)).perform();;
			
		}
		
		public void doActionSendKey(By locator, String value) {
			Actions act = new Actions(driver);
			act.sendKeys(getElement(locator), value).perform();
		}
		
		/***************Wait utils ****************/
		
		
		/*
		 *  An expectation for checking an element is visible and enabled such that you can click it.
		 * @param locator
		 * @param timeOut
		 */
		public void clickElementReady(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
			
		}
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page. 
		 * This does not necessarily mean that the element is visible.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementPresence(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page and visible. 
		 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementVisible(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		
		/**
		 * An expectation for checking that there is at least one element present on a web page.
		 * @param locator
		 * @param timeOut
		 * @return 
		 */
		public List<WebElement> waitForElementsPresence(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		
		/**
		 * An expectation for checking that all elements present on the web page that match the locator are visible. 
		 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
		 */
		public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		public List<WebElement> waitForElementsVisibleByFW(By locator, int timeOut, int pollingtimeOut) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
										.withTimeout(Duration.ofSeconds(timeOut))
										.pollingEvery(Duration.ofSeconds(pollingtimeOut))
										.ignoring(NoSuchElementException.class)
										.withMessage(ELEMENT_TIME_OUT_MESSAGE);
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		
		public String waitForTitleContains(String title, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				if(wait.until(ExpectedConditions.titleContains(title))) {
					return driver.getTitle();
				}
			} catch (Exception e) {
				System.out.println("Title is not found witin" + timeout);
				e.getStackTrace();
				// TODO: handle exception
			}
			return null;
		}
		
		public String waitForTitleIs(String title, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				if(wait.until(ExpectedConditions.titleIs(title))) {
					return driver.getTitle();
				}
			} catch (Exception e) {
				System.out.println("Title is not found witin" + timeout);
				e.getStackTrace();
				// TODO: handle exception
			}
			return null;
		}
		public String waitForUrlContains(String url, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				if(wait.until(ExpectedConditions.urlContains(url))) {
					return driver.getCurrentUrl();
				}
			} catch (Exception e) {
				System.out.println("Url is not found witin" + timeout);
				e.getStackTrace();
				// TODO: handle exception
			}
			return null;
		}
		public String waitForUrlIs(String url, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				if(wait.until(ExpectedConditions.urlToBe(url))) {
					return driver.getCurrentUrl();
				}
			} catch (Exception e) {
				System.out.println("Url is not found witin" + timeout);
				e.getStackTrace();
				// TODO: handle exception
			}
			return null;
		}
		
		public Alert waitForJSAlert(int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		public String getAlertText(int timeout) {
			return waitForJSAlert(timeout).getText();
		}
		public void acceptAlert(int timeout) {
			 waitForJSAlert(timeout).accept();
		}
		public void dismissAlert(int timeout) {
			 waitForJSAlert(timeout).dismiss();
		}
		public void dismissAlert(int timeout, String value) {
			 waitForJSAlert(timeout).sendKeys(value);
		}
		public void waitForFrameAndSwitchTo(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		}
		public void waitForFrameAndSwitchTo(int frameIndex, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		}
		public void waitForFrameAndSwitchTo(WebElement ele, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(ele));
		}
	}
