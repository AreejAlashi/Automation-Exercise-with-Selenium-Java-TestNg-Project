package com.axos.goup.automationExercise;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class projectTest extends TestBase {
	private Homepage homepage;
	private Products products;
	private Cart cart;
	
	@BeforeMethod
	public void setUpTest() {
		homepage=new Homepage(driver);
		products=new Products(driver);		
		cart    =new Cart(driver);
	}
	
@Test(groups= {"Home"},priority=1)
// verify The product text link is changed to orange after hover
public void verifyProductLinkHoverColor() throws InterruptedException{
	homepage.hoverProduct();
	Thread.sleep(1000);
	homepage.getColorProductLink();
	String expectedColor="rgba(254, 152, 15, 1)";
	String actualcolor=homepage.getColorProductLink();
	Assert.assertEquals(actualcolor, expectedColor, "Product link color did not change to orange after hover!");
  }

@Test(groups= {"Home"},priority=2) 
// verify that product text link is clickable
public void verifyClickProductLink() {
	homepage.clickProdctLink();
    String actualURL =homepage.getProUrl();
    String expectedURL="https://automationexercise.com/products";
    Assert.assertEquals(actualURL, expectedURL, "The product Link Text is not clickable");
   }

@Test(groups= {"products"},priority=3)
// verify that the product search bar is inabled to be written in
public void verifyTypingProSearchBar() throws InterruptedException {
	products.clickproSearchBar();
	products.sendkeySearchBar("Clothes");
	String actual=products.getSearchBarText();
	Thread.sleep(1000);
	Assert.assertEquals(actual, "Clothes", "You can not type on searchBar");
   }

@Test(groups= {"products"},priority=4)
// verify that when search for empty search bar All products is displayed
public void verifyResultEmptySearchBar() {
	products.clearSearchBar();
	int productsNumBeforeSearch= products.getNumbersOfProducts();
	products.clickproSearchBar();
	products.clickSearchIcon();
	int productsNumAfterSearch=products.getNumbersOfProducts();
	Assert.assertEquals(productsNumBeforeSearch, productsNumAfterSearch);
  }

@Test(groups= {"products"},priority=5)
//Verify that when search for existed product the system back with the same keywords entered
public void verifySearchResultsContainKeyword() throws InterruptedException {
	String keyword="top";
	products.clickproSearchBar();
	products.sendkeySearchBar(keyword);
	products.clickSearchIcon();
	 Thread.sleep(3000);
   List<String> proNames= products.getAllProuctnames();
   for(String name:proNames) {
	   System.out.println(name);
   Assert.assertTrue(name.toLowerCase().contains(keyword.toLowerCase()),"The Keyword is not existed");	
        }
}


@Test(groups= {"products"} ,priority=6)
//Verify that when search for not existed products the system back with empty page
public void verifySearchResultsForNotExistedKeyword() {
	products.clearSearchBar();
	String keword="@!";
	products.clickproSearchBar();
	products.sendkeySearchBar(keword);
	products.clickSearchIcon();
	List<String> result=products.getAllProuctnames();
	Assert.assertTrue(result.isEmpty(),"ERROR: Products were found for a non-existing keyword!");
}

    
@Test(groups= {"products"} ,priority=7)
//verify that all existed choices is inserted under KIDS category
public void verifyNumberOfListunderKids() {
	products.kidsClick();
    List<WebElement> options=products.getkidsOptions();
    int actualcount=options.size();
    Assert.assertEquals(actualcount, 2, "number o items under kids is not correct");

}

@Test(groups= {"products"},priority=8)
//verify the names under kids category
public void verifyTitlesOfListUnderKids() {
	List<WebElement>options1=products.getkidsOptions();
	List<String> expectNames=Arrays.asList("DRESS","TOPS & SHIRTS");
	List<String> actualNames=new ArrayList<>();
	for(WebElement option:options1) {
		actualNames.add(option.getText().trim());
	}	
	Assert.assertEquals(actualNames, expectNames);
	System.out.println(actualNames);
}

@Test(groups= {"products"} , priority=9)
//verify that after clicking on Dress choice under KIDS category the title above changed
public void verifychangingHeadTitleForKidsDress() throws InterruptedException {
	products.scrollDown();
	products.kidsClick();
	products.clickDressKids();
	Thread.sleep(1000);
	String actual=products.getkidsDressTitle();
	Assert.assertEquals(actual, "KIDS - DRESS PRODUCTS", "Wrong Title is displayed!!");
}

@Test(groups= {"products"}, priority =10 )
//verify that we can add product to cart after hovering over the product's image
public void hoverOverAproductAndAddToCart() throws InterruptedException {
	products.scrollDown();
	products.hoverOverProduct();
	products.expectedProductName  =products.getProductNameAfterHover();
	products.expectedProductPrice  =products.getProductPriceAfterHover();
	products.clickAddFromOvelay();
	Thread.sleep(3000);
boolean expected=products.addconfirmMessage();
Assert.assertTrue(expected, "No confirmation message is displayed!!");
}

//verify that after click on view cart from the pop the system redirected you to cart
@Test(groups= {"products"}, priority=11)
public void isRedirectedToCart() throws InterruptedException {
	products.clickViewCartPop();
	Thread.sleep(5000);
	String actualResult = products.getUrl();
	String expectedResult="https://automationexercise.com/view_cart";
	Assert.assertEquals(actualResult, expectedResult);
}

//verify the Cart Structure
@Test(groups= {"Cart"}, priority=12)
public void cartStructureContentCheck() {
	List<String> actual=cart.getCartHeader();
	List<String> expected = Arrays.asList("Item", "Description", "Price", "Quantity", "Total");
	Assert.assertEquals(actual,expected,"Cart header structure is incorrect!" );
}

//Verify that the same product description and price is added to cart
@Test(groups= {"Cart"} , priority=13,dependsOnMethods = {"hoverOverAproductAndAddToCart"})
public void verifyProductDetailsInCart() {
	String actualProNmae  = cart.getProNameFromCart();
	String actualProPrice=cart.getProPriceFromCart();
	Assert.assertEquals(actualProPrice, products.expectedProductPrice,"No matching");
	Assert.assertEquals(actualProNmae, products.expectedProductName,"No matching");
}
@Test(groups= {"Cart"} , priority=14)
//to verify deleting from Cart
public void deleteFromCart() throws InterruptedException {
	cart.deletInCart();
	Thread.sleep(1000);
	String actualMessage=cart.getConfirmDeleting();
Assert.assertTrue(actualMessage.contains("empty"), "The prooduct is not deleted");
}

@Test(groups= {"Cart"},  priority=15 )
public void addToCartAnotherWay() throws InterruptedException {
	cart.hereClick();
	products.kidsClick();
	Thread.sleep(1000);
	products.clickDressKids();
	Thread.sleep(5000);
	products.clickViewSecondPro();
	products.addToCartView();
	Thread.sleep(5000);
	boolean expected=products.addconfirmMessage();
	Assert.assertTrue(expected, "No confirmation message is displayed!!");
	Thread.sleep(1000);
	products.clickContinueShopping();
}
  
@Test(groups= {"Cart"},  priority=16 )
public void changeQuanToZero() throws InterruptedException {
	products.backSpaceQuan();
	products.sendKeysQuan("0");
	products.addToCartView();
	Thread.sleep(5000);
	products.clickContinueShopping();
boolean expected=products.addconfirmMessageNO();
Assert.assertFalse(expected,"Unexpected: confirmation message appeared for zero quantity!");
}


@Test(groups= {"Cart"},  priority=17 )
public void changeQuanToNegative() throws InterruptedException {
	products.backSpaceQuan();
	products.sendKeysQuan("-1");
	products.addToCartView();
	Thread.sleep(5000);
	products.clickContinueShopping();	
boolean expected=products.addconfirmMessageNO();
Assert.assertFalse(expected, "Unexpected: confirmation message appeared for negative quantity!");
	}

@Test(groups= {"Cart"},  priority=18 )
public void changeQuanToHugeNum() throws InterruptedException {
	products.backSpaceQuan();
	products.sendKeysQuan("1000000597865256565265262662252");
	products.addToCartView();
	Thread.sleep(5000);
	products.clickContinueShopping();
	boolean expected=products.addconfirmMessageNO();
	Assert.assertFalse(expected, "Unexpected: confirmation message appeared for huge quantity!");
	}	
}


//verify that clicking multible times increased the quantity











