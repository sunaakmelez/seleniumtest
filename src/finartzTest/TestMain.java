package finartzTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.Assert;

public class TestMain {
	static String siteUrl = "https://www.finartz.com";
	static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
		driver_settings();
		opening();
		
		Thread.sleep(1000);
		
		solutions_open();
		
		List<String> list = read_header();
		
		Thread.sleep(1000);
        
        blog_open();
        
        medium_open();
        
        search_button(list);
        
        closing();
		
    }
	
	public String getText(WebDriver driver) {
		return driver.getCurrentUrl();
	}
	
	public static void driver_settings() {
		System.setProperty("webdriver.gecko.driver", "C:\\SeleniumGecko\\geckodriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        driver = new FirefoxDriver();
	}
	
	public static void opening() {
		driver.get(siteUrl);
        Assert.assertTrue(driver.getTitle().equals("Finartz - Homepage"));
		System.out.println("Anasayfa açıldı.");
    }
	
	public static void solutions_open() {
		WebElement solutions = driver.findElement(By.linkText("Solutions"));
		
		if(solutions.isDisplayed())
			solutions.click();
		else {
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			solutions_open();
		}
		Assert.assertTrue(driver.getTitle().equals("Solutions - Finartz"));
		
		System.out.println("Solutions sayfası açıldı.");
	}
	
	public static List<String> read_header() {
		List<WebElement> headersList = new ArrayList<WebElement>();
        List<String> headerListS = new ArrayList<String>();
        for(int i=1; i<4; i++)
        	headersList.add(driver.findElement(By.xpath("/html/body/section[" + i + "]/div/div[1]/h2")));
        
        System.out.println("Finartz Innovative Solutions: ");
        
        for (WebElement webElement : headersList) {
            System.out.print(webElement.getText() + " ");
            headerListS.add(webElement.getText());
        }
        System.out.println("\n");
		return headerListS;
	}
	
	public static void blog_open() throws InterruptedException{
		WebElement blog = driver.findElement(By.linkText("Blog"));
		if(blog.isDisplayed()) {
			blog.click();
		}else {
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			blog_open();
		}

        new_tab();
        Thread.sleep(5000);
        
		Assert.assertTrue(driver.getTitle().equals("Finartz"));
		System.out.println("Blog açıldı.");
	}

	//Switch to new tab
	public static void new_tab() {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
		@SuppressWarnings("unchecked")
		ArrayList tabs = new ArrayList(driver.getWindowHandles());
		driver.switchTo().window((String) tabs.get(1));
	}
	
	public static void medium_open(){
		WebElement medium = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/div[2]/div[1]/div/a"));
		if(medium.isDisplayed()) {
			medium.click();
		}else {
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			medium_open();
		}
		Assert.assertTrue(driver.getTitle().equals("Medium – a place to read and write big ideas and important stories"));
		System.out.println("Medium sayfası açıldı.");
	}
	
	public static void search_button(List<String> list) throws InterruptedException {
		WebElement searchButton = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/div[2]/div[2]/div/label"));
		searchButton.click();
		Random rn = new Random();
		int index = rn.nextInt(list.size());
		WebElement searchBox = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/div[2]/div[2]/div/label/input"));
        searchBox.sendKeys(list.get(index));
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        Assert.assertTrue(driver.getTitle().equals("Search and find – Medium"));
        System.out.println("Arama sayfası açıldı.");
	}
		
	public static void closing() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();
		System.out.println("Tarayıcı kapatıldı.");
	}
}

