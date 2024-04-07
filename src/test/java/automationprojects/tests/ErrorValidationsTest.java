package automationprojects.tests;

	
	
	

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import automationprojects.TestComponents.BaseTest;
import automationprojects.TestComponents.Retry;
import automationprojects.pageobjects.CartPage;
import automationprojects.pageobjects.LandingPage;
import automationprojects.pageobjects.ProductCatalogue;

	//using page object model in test annotation
	public class ErrorValidationsTest extends BaseTest {
		
		@Test(groups= {"ErrorHandling"}, retryAnalyzer=Retry.class)//if test fails it will re run
		public void LoginErrorValidation() throws IOException, InterruptedException //This is Test case
		
		{
			
		String productName="ZARA COAT 3";
		landingPage.loginApplication("balasingamji@gmail.com","Bhuvana2");
		//Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		//to check the fail test case eraring some tet
		Assert.assertEquals("Incorrect email  password.", landingPage.getErrorMessage());
			
					
		}
		@Test
		public void ProductErrorValidation() throws IOException, InterruptedException //This is Test case
		
		{
			
		String productName="ZARA COAT 3";
		LandingPage landingPage=launchApplication();
		ProductCatalogue productCataloguePage = landingPage.loginApplication("balasingamji@gmail.com","Bhuvana12");
		
		List<WebElement> products = productCataloguePage.getProductList();
		productCataloguePage.addProductToCart(productName);
		CartPage cartPage= productCataloguePage.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 35");
		Assert.assertFalse(match);
		
	}

	}
	
	




