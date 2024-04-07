package automationprojects.TestComponents;



	import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.nio.charset.StandardCharsets;
	import java.time.Duration;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Properties;

	import org.apache.commons.io.FileUtils;
	import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.edge.EdgeDriver;
	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.BeforeMethod;

	import com.fasterxml.jackson.core.type.TypeReference;
	import com.fasterxml.jackson.databind.ObjectMapper;

	import automationprojects.pageobjects.LandingPage;
	import io.github.bonigarcia.wdm.WebDriverManager;

	public class BaseTest {
		public WebDriver driver;
		public LandingPage landingPage;
		public WebDriver InitializeDriver() throws IOException
		{
			//properties is a class
			
			Properties prop=new Properties();
			FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"//src//main//java//automationprojects//resources//GlobalData.properties");
			//FileInputStream fis=new FileInputStream("C:\\Users\\bhuvaneshwari\\eclipse-workspace\\SeleniumrameworkDesign\\src\\test\\java\\bhuvanaProjects\\resources\\GlobalData.properties");
			prop.load(fis);
			//DECIDE AT RUN TIME DEPENDS THE NEED OF WHICH BROWSER WE SHOULD RUN
			String browserName=System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
			
			//String browserName= prop.getProperty("browser");
			
			//if(browserName.equalsIgnoreCase("chrome"))
			if(browserName.contains("chrome"))
			{
				// below two lines for headless mode working for chrome
				ChromeOptions options = new ChromeOptions(); //1.
				WebDriverManager.chromedriver().setup();
				if(browserName.contains("headless"))// this if for the if contains chrome then looking for headless
				{
				options.addArguments("headless");//2.
				}
				driver=new ChromeDriver(options);//only pass the variable
				driver.manage().window().setSize(new Dimension(1440,900)); // maximum window size to visible elements

			}
			else if(browserName.equalsIgnoreCase("firefox"))
				
			{
				//firefox
				//System.setProperty("webdriver.gecko.driver","/users/bhuvaneswari//documents//geckgodriver");
				//driver=new FirefoxDriver();

			}
			else if(browserName.equalsIgnoreCase("edge"))
				
			{
				System.setProperty("webdriver.edge.driver", "edge.exe");
				driver = new EdgeDriver();
			}
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().window().maximize();
			return driver;
			 
		}
		
		
		
		
		
		// receiving data here as filepath
		
		public List<HashMap<String,String>> getJsonDataToMap(String filePath) throws IOException
		{
			//Read json to string
			String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
			//String to HashMap Jackson Databind
			ObjectMapper mapper= new ObjectMapper();
			List<HashMap<String,String>> data= mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
			return data;
			
				
			}
		
		//to print the or screenshot of testcase fail

		public String getScreenshot(String testCaseName,WebDriver driver) throws IOException// to capture failure testcase
		{
			TakesScreenshot ts = (TakesScreenshot)driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File file= new File(System.getProperty("user.dir")+"//reports//"+testCaseName + ".png");
			FileUtils.copyFile(source, file);
			return System.getProperty("user.dir")+"//reports//"+testCaseName + ".png";
			//return file;
		}

		@BeforeMethod(alwaysRun=true)
		public LandingPage launchApplication() throws IOException
		{
			driver= InitializeDriver();
			landingPage = new LandingPage(driver);
			landingPage.goTo();
			return landingPage;
		}
		
		@AfterMethod(alwaysRun=true)
		public void tearDown()
		{
			driver.close();

		}
		
	}

