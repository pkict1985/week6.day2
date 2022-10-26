package week6.Day2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sukgu.Shadow;

public class ShadowDom {
public static void main(String[] args) {
	//Launch ServiceNow application
	WebDriverManager.chromedriver().setup();
	ChromeDriver driver=new ChromeDriver();
	driver.get("https://dev137890.service-now.com/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
	Actions builder=new Actions(driver);
	//Login with valid username and password
	driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys("admin");
	driver.findElement(By.xpath("//input[@id='user_password']")).sendKeys("Testleaf@123");
	driver.findElement(By.xpath("//button[text()='Log in']")).click();
	//Click All
	Shadow shadow=new Shadow(driver);
	shadow.setImplicitWait(20);
	shadow.findElementByXPath("//div[text()='All']").click();
	//Click Incidents in Filter Navigator
	shadow.findElementByXPath("//span[text()='Incidents']").click();
	//Click on Create New Option and fill the mandatory fields
	WebElement frameElement=shadow.findElementByXPath("//iframe[@title='Main Content']");
	driver.switchTo().frame(frameElement);
	driver.findElement(By.xpath("//button[text()='New']")).click();
	//Generated incidentnumber
	String incidentNo = driver.findElement(By.xpath("//input[@id='incident.number']")).getAttribute("value");
	WebElement incidentSDesc = driver.findElement(By.xpath("//input[@id='incident.short_description']"));
	builder.moveToElement(incidentSDesc).perform();
	incidentSDesc.sendKeys("New Desc");
	driver.findElement(By.xpath("(//button[text()='Submit'])[1]")).click();
	driver.switchTo().defaultContent();
	//Verify the newly created incident by getting the incident number
	//and put it in a search field and enter, then verify whether incident no created or not
	WebElement ifElement = shadow.findElementByXPath("//iframe[@id='gsft_main']");
	driver.switchTo().frame(ifElement);
	WebElement searchTxtElement = driver.findElement(By.xpath("(//input[@placeholder='Search'])[1]"));
	//builder.moveToElement(searchTxtElement).perform();
	searchTxtElement.sendKeys(incidentNo, Keys.ENTER);
	String searchedIncidentNo = driver.findElement(By.xpath("//tbody[@class='list2_body']//tr//td[3]/a")).getText();
	driver.switchTo().defaultContent();
	//Confirm incident exists
	if(incidentNo.equals(searchedIncidentNo))
		System.out.println(incidentNo + " incident Number already exist!");
	else
		System.out.println(incidentNo + " incident Number does not exist!");
	
	//driver.close();
}
}
