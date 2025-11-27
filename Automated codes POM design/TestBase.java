package com.axos.goup.automationExercise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	protected WebDriver driver;
	private static String URL="https://automationexercise.com/";
	
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL);
	}
	
	@AfterTest
	public void tearDown() {
		if(driver!= null) {
	driver.quit();
			
	}
}
  }