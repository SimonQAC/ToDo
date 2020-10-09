package com.qa.todo.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CreateTaskTest {

	public static WebDriver driver;
	
	@BeforeClass
	public static void setup() {
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		System.setProperty("wevdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1280,720));
	}
	
	@Test
	public void createTaskPageTest() {
		driver.get("http://127.0.0.1:5500/html/index.html");
        driver.findElement(By.xpath("(//*[@id=\"navbarNavAltMarkup\"]/div/a[2]")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
}
