package automationprojects.tests;

	

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationprojects.pageobjects.CartPage;
import automationprojects.pageobjects.CheckoutPage;
import automationprojects.pageobjects.ConfirmationPage;
import automationprojects.pageobjects.LandingPage;
import automationprojects.pageobjects.OrderPage;
import automationprojects.pageobjects.ProductCatalogue;
import automationprojects.TestComponents.BaseTest;
//new code CICD
	//using page object model in test annotation
	public class SubmitOrderTest extends BaseTest {
		String productName="ZARA COAT 3";
		
		@Test(dataProvider="getData",groups= {"Purchase"})

		public void SubmitOrder(HashMap<String,String> input) throws IOException, InterruptedException //This is Test case
		
		{
		//String productName="ZARA COAT 3";

		ProductCatalogue productCataloguePage = landingPage.loginApplication(input.get("email"),input.get("password"));
		
		List<WebElement> products = productCataloguePage.getProductList();
		productCataloguePage.addProductToCart(input.get("product"));
		CartPage cartPage= productCataloguePage.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage=cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage =confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
				
		}
		
		@Test(dependsOnMethods= {"SubmitOrder"})
		public void OrderHistoryTest()
		{
			//"ZARA COAT 3";
			ProductCatalogue productCatalogue = landingPage.loginApplication("balasingamji@gmail.com","Bhuvana12");
			OrderPage ordersPage = productCatalogue.goToOrdersPage();
			Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName)); 
			
		}
		
		//when testcase fails , this method take screenshot
		
		public String getScreenshot(String testCaseName) throws IOException
		{
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File file = new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
			FileUtils.copyFile(source, file);
			return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
		}
		
		
		
		
		@DataProvider
		public Object[][] getData() throws IOException 
		{
			/*HashMap<String,String> map = new HashMap<String,String>();
			map.put("email", "balasingamji@gmail.com");
			map.put("password", "Bhuvana12");
			map.put("product", "ZARA COAT 3");
			
			HashMap<String,String> map1 = new HashMap<String,String>();
			map1.put("email", "sunmoon1992@gmail.com");
			map1.put("password", "Sunmoon12");
			map1.put("product", "ADIDAS ORIGINAL");
			return new Object[][] {{map},{map1}}; 	*/
			
			// calling this parent class method getJsonDatato map, need not create object, becz we inherit
			List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//automationprojects//data//PurchaseOrder.json");
			return new Object[][] {{data.get(0)}, {data.get(1)}}; 
			}
		
		/* to use the Json we need Datareader class to map json with hashMap */
		
		
		/*@DataProvider
		public Object[][] getData()
		{
			return new Object[][] {{"balasingamji@gmail.com","Bhuvana12","ZARA COAT 3"},{"sunmoon1992@gmail.com","Sunmoon12","ADIDAS ORIGINAL"}};
		} */
		
	}

	
	
	



	
	

