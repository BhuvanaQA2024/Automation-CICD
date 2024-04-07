package automationprojects.pageobjects;



	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;

	import automationprojects.AbstractComponents.AbstractComponent;

	public class ConfirmationPage extends AbstractComponent
	{
		public ConfirmationPage(WebDriver driver) {
			super(driver);
			this.driver=driver;
			PageFactory.initElements(driver,this);
		}	

		WebDriver driver;
		
		
		
		@FindBy(css=".hero-primary")
		WebElement confirmationMessage;

			public String getConfirmationMessage() 
		{
			return confirmationMessage.getText();
		}

	}
		
		
	
	

