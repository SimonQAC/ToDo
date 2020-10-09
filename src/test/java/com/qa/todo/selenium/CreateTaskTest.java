package com.qa.todo.selenium;

import org.junit.BeforeClass;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CreateTaskTest {

	public static WebDriver driver;
	
	@BeforeClass
	public static void init() {
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		System.setProperty("wevdriver.chrome.driver", "C:\\Users\\Work\\Documents\\workspace-spring-tool-suite-4-4.8.0.RELEASE\\ToDo\\src\\test\\resources\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1280,720));
	}
}
