package com.axos.goup.automationExercise;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Homepage {
	
  private WebDriver driver;
  
  public Homepage (WebDriver driver) {
	  
	  this.driver=driver;	  	  
  }
//Locators 

  private By productTextLink = By.xpath("//a[@href='/products']");
  private By proRedirectPage = By.xpath("//*[text()='All Products']");

//Methods

  // Hover over product link 
  public void hoverProduct() {
	 WebElement productLink=driver.findElement(productTextLink);
     Actions hover=new Actions(driver);
     hover.moveToElement(productLink).perform();    
  }
  
  // Get the orange text color
  public String getColorProductLink() {
	  WebElement link= driver.findElement(productTextLink);
	  return link.getCssValue("color");
  }
  
  // click on the product link 
  public void clickProdctLink() {
	  WebElement linkClick=driver.findElement(productTextLink);
	  linkClick.click();
  }
  
//Get the product URL
  public String getProUrl() {
	  return driver.getCurrentUrl();  
	  
  }
  
  //make sure of redirection to product page
public boolean isRedirectToProuctPage() {
	return driver.findElement(proRedirectPage).isDisplayed();
}


  
  
  
  
  
  
}