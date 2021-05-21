package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class blazeDemo {

	// The Scenario is choose respective flight and book for lowest price
	@Test
	public void bookFlight() {

		// chromedriver is available in local folder called resource of the project
		// hence the code would be most OOP
		//The Script Developed in MAC machine hence the chromedirver path with single /
		System.setProperty("webdriver.chrome.driver", "./resource/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.get("https://blazedemo.com/");

		// validating Header of the page
		String expectedHeader = "Welcome to the Simple Travel Agency!";

		String actualHeader = driver.findElement(By.tagName("h1")).getText();

		if (actualHeader.equalsIgnoreCase(expectedHeader)) {
			System.out.println("Header validation Passed!");
		} else {
			System.out.println("Header validation Failed");
		}

		// Select departure & destination from dropdown
		Select dropdowndepar = new Select(driver.findElement(By.name("fromPort")));
		dropdowndepar.selectByIndex(0);
		System.out.println("Selected departure from list is : " + dropdowndepar.getFirstSelectedOption().getText());

		Select dropdowndesti = new Select(driver.findElement(By.name("toPort")));
		dropdowndesti.selectByIndex(1);
		System.out.println("Selected destination from list is : " + dropdowndesti.getFirstSelectedOption().getText());

		driver.findElement(By.xpath("//*[@class='btn btn-primary']")).click();

		// list all the price
		List<WebElement> price = driver.findElements(By.xpath("//tr/td[6]"));
		System.out.println("list of price items are : " + price.size());

		// put all the prices into array list and get the lowest price
		ArrayList<String> prices = new ArrayList<String>();
		for (int i = 0; i < price.size(); i++) {
			System.out.println(price.get(i).getText());
			String priceint = String.valueOf(price.get(i).getText().substring(1));
			System.out.println(priceint);
			prices.add(priceint);
		}
		// get the lowest price from the list
		String minprice = Collections.min(prices);
		System.out.println("min price is : " + minprice);

		// Choose This Flight on lowest price
		List<WebElement> bttns = driver.findElements(By.xpath("//*[@class='btn btn-small']"));
		System.out.println("total list of buttons are :" + bttns.size());
		for (int i = 0; i < price.size(); i++) {
			String priceint1 = String.valueOf(price.get(i).getText().substring(1));
			if (priceint1.equals(minprice)) {
				bttns.get(i).click();
				break;
			}
		}

		// filling the form
		driver.findElement(By.id("inputName")).sendKeys("Kumar Akkala");
		driver.findElement(By.id("address")).sendKeys("123 Main St.");
		driver.findElement(By.id("address")).sendKeys("Anytown");
		driver.findElement(By.id("state")).sendKeys("State");
		driver.findElement(By.id("zipCode")).sendKeys("12345");
		Select cardType = new Select(driver.findElement(By.id("cardType")));
		cardType.selectByIndex(0);
		System.out.println("Selected CardType is : " + cardType.getFirstSelectedOption().getText());
		driver.findElement(By.id("creditCardNumber")).sendKeys("1234 5678 9012 3456");
		driver.findElement(By.id("creditCardMonth")).clear();
		driver.findElement(By.id("creditCardMonth")).sendKeys("05");
		driver.findElement(By.id("creditCardYear")).clear();
		driver.findElement(By.id("creditCardYear")).sendKeys("2021");
		driver.findElement(By.id("nameOnCard")).sendKeys("Kumar Akkala");
		driver.findElement(By.xpath("//*[@class='btn btn-primary']")).click();

		// validate the Transaction Confirmation
		String expectedTransaction = "Thank you for your purchase today!";
		String actualTransaction = driver.findElement(By.tagName("h1")).getText();

		if (actualTransaction.equalsIgnoreCase(expectedTransaction)) {
			System.out.println("Booking Functionality is Passed ! with Transaction ID : "
					+ driver.findElement(By.xpath("//tr[1]/td[2]")).getText());
		}else {
			System.out.println("Booking Functionality Failed");
		}

		driver.quit();

	}
}
