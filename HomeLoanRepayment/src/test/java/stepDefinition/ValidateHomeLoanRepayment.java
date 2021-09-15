package stepDefinition;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import resources.Utilities;

public class ValidateHomeLoanRepayment {
	static WebDriver driver;
	static String totalLoanRepayment =null;
	static String totalInterestRepay =null;
	static int testcaseNumber = 0;
	static String errorMessage=null;

	@Before
    public void initializeBrowserDriver(){
		System.setProperty("webdriver.gecko.driver", "C:\\Discount Central\\Automation\\HomeLoanRepayment\\geckodriver.exe");
		  driver = new FirefoxDriver();
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		  testcaseNumber =testcaseNumber+1;
    }	
	
	@Given("User Launched {string}")
	    public void user_launched_something(String url) throws Throwable {
		
		    driver.get(url);
		    String homeLoaninHomePage=driver.findElement(By.linkText("Home loans")).getText();
		    Thread.sleep(1000);
		    Utilities.screenshot(driver, System.currentTimeMillis(),"Home Page Launched Successfully",testcaseNumber);
		    assertEquals(homeLoaninHomePage,"Home loans");
	    }

	 @When("^User Entered   (.+),(.+),(.+),(.+)and clicked on Calculate$")
	public void user_entered_and_clicked_on_calculate(String borrowamount, String duration, String repaymentValue, String interestrate) throws Throwable {	    
		    WebElement amount=driver.findElement(By.id("amount"));
			amount.clear();
			amount.sendKeys(borrowamount);
			WebElement term=driver.findElement(By.id("term"));
			term.clear();
			term.sendKeys(duration);
	    	Select repaymentType = new Select(driver.findElement(By.id("interestOnly")));
	    	List <WebElement> repaymentOptions = repaymentType.getOptions();
     		
			
//         	WebElement productWebelements = driver.findElement(By.xpath("//select[@id='productId']"));
//	    	List <WebElement> productOptionsOwnerOccupied =  (productWebelements.findElement(By.xpath("//optgroup[@label='Owner occupied (Interest only)']"))).findElements(By.tagName("options"));
//	    	List <WebElement> productOptionInterest =  (productWebelements.findElement(By.xpath("//optgroup[@label='Investment (Interest only)']"))).findElements(By.tagName("options"));
//	    	List <WebElement> productOptions = productWebelements.findElements(By.tagName("options"));

//    	    productOptions.addAll(productOptionsOwnerOccupied);
//	    	productOptions.addAll(productOptionInterest);
	    	boolean repaymentFlag =Utilities.validateDropDownList(repaymentValue,repaymentOptions);
			if(repaymentFlag)
			{
				repaymentType.selectByIndex(Utilities.indexOfDropDown(repaymentValue, repaymentOptions));
			    Thread.sleep(1500);
			}
			assertEquals("Valid repaymentType", true, repaymentFlag);
			Select products = new Select(driver.findElement(By.id("productId")));
			List <WebElement> productOptions = products.getOptions();
			boolean productFlag =Utilities.validateDropDownList(interestrate,productOptions);
			if(productFlag)
			{
                Thread.sleep(1000);
				products.selectByIndex(Utilities.indexOfDropDown(interestrate, productOptions));
		       	Thread.sleep(1500);
			}
			assertEquals("Valid Interest Rate", true, productFlag);
			if(Utilities.validateAmount(Integer.parseInt(borrowamount), interestrate, productOptions).equalsIgnoreCase("min5000"))
			{
				errorMessage=driver.findElement(By.className("error-block")).getText();
				assertEquals("Error throw for the input Amount less than 5000 for Comm Green Loan & Investmentplan", errorMessage,"$5,000 minimum loan amount" );
			}
			else if(Utilities.validateAmount(Integer.parseInt(borrowamount), interestrate, productOptions).equalsIgnoreCase("min10000"))
			{
				errorMessage=driver.findElement(By.className("error-block")).getText();
				assertEquals("Error throw for the input Amount less than 10000 for plans except Comm Green Loan & Investmentplan", errorMessage,"$10,000 minimum loan amount" );
			}
			else if(Utilities.validateAmount(Integer.parseInt(borrowamount), interestrate, productOptions).equalsIgnoreCase("ValidAmount"))
			{
				
				assertEquals("ValidAmount", true,true);
			}
			
			Thread.sleep(1000);
			Utilities.screenshot(driver, System.currentTimeMillis(),"Input Fields Calculator",testcaseNumber);
			if (driver.findElement(By.id("submit")).isDisplayed())
			{
				driver.findElement(By.id("submit")).click();
			}
			
			Thread.sleep(1000);
		
    }

	 @Then("Verify Total Loan Repayment and Total Interest charged")
	    public void total_loan_repayment_and_total_interest_charged() throws Throwable {
		 totalLoanRepayment =driver.findElement(By.xpath("//span[@data-tid='total-repayment']")).getText();
	      Thread.sleep(1000);
	      totalInterestRepay =driver.findElement(By.xpath("//span[@data-tid='total-interest']")).getText();
          System.out.println(totalLoanRepayment);
	      System.out.println(totalInterestRepay);
	      assertEquals("Total Loan Repayment Calculated as "+totalLoanRepayment+"",true,totalLoanRepayment!=null);
	      assertEquals("Total Interest Repayment Calculated as "+totalInterestRepay+"",true,totalInterestRepay!=null);
	      Utilities.screenshot(driver, System.currentTimeMillis(),"Total Loan Repayment & Total Interest repay for Record",testcaseNumber);
	    }

	    @Given("Navigate to Home Loan Repayment Calculator")
	    public void navigated_to_home_loan_repayment_calculator() throws Throwable {
//	    	driver.findElement(By.linkText("Home loans")).click();
	    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	    	//JavascriptExecutor js = (JavascriptExecutor) driver;
	    	//js.executeScript("window.scrollBy(0,250)", "");
	    	WebElement repaymentLink = driver.findElement(By.linkText("Repayments calculator"));
	    	((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", repaymentLink);
	    	Thread.sleep(1000);
	    	 Utilities.screenshot(driver, System.currentTimeMillis(),"Repayment Calculator Link",testcaseNumber);
	    	repaymentLink.click();
	    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	    	String calculatorText = driver.findElement(By.xpath("//h6[text()='Repayment calculator']")).getText();
	    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	    	Utilities.screenshot(driver, System.currentTimeMillis(),"Home Repayment Calculator Navigation",testcaseNumber);
	    	assertEquals("Repayment calculator", calculatorText);
	    	//driver.close();
	    	}
	    @After public void closeBrowser(){ 
	    
	        driver.close(); 
	     } 


}
