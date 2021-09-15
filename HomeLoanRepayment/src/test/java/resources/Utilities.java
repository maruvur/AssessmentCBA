package resources;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Utilities {

	public static void screenshot(WebDriver driver, long ms, String ExpectedResult, int testcaseNumber) {

		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String fileSuffix = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			FileUtils.copyFile(source, new File("./ScreenShots/Testcase -" + testcaseNumber + "-" + fileSuffix + "/"
					  + ExpectedResult + ".png"));
			System.out.println("ScreenShot Taken");
		} catch (Exception e) {
			System.out.println("Exception while taking ScreenShot " + e.getMessage());
		}

	}

	public static boolean validateDropDownList(String testData, List<WebElement> dropDownList) {
		boolean validate = false;
		for (WebElement options : dropDownList) {
						
			if (testData.trim().equalsIgnoreCase(options.getText().trim()))
			{
				
				validate = true;
			}
		}
		return validate;
	}
	public static int indexOfDropDown(String testData, List<WebElement> dropDownList) {
		int index =0;
		
		for (int i=0;i <dropDownList.size();i++)
		{
			
			if (testData.equalsIgnoreCase(dropDownList.get(i).getText()))
			{
				index =i;
				break;
			}
		}
		return index;
	}


	public static String validateAmount(int amount, String productType, List<WebElement> productList) {
		String validAmount = null;
		if (validateDropDownList(productType, productList)) {

			if (amount < 5000 && (productType.equalsIgnoreCase("0.99% p.a. CommBank Green Loan")
					|| productType.equalsIgnoreCase("0.99% p.a. CommBank Green Investment Loan"))) {
				validAmount = "min5000";
			} else if (amount < 10000 && (!productType.equalsIgnoreCase("0.99% p.a. CommBank Green Loan")
					|| !productType.equalsIgnoreCase("0.99% p.a. CommBank Green Investment Loan"))) {
				validAmount = "min10000";
			}
			else
				validAmount = "ValidAmount";
		}
		return validAmount;

	}
}
