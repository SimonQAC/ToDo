package com.qa.todo.selenium;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
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
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
//		options.setHeadless(true);
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1280,720));
	}
	
	@Test
	public void createTaskPageTest() {
		driver.get("http://127.0.0.1:5500/html/index.html");
        driver.findElement(By.xpath("/html/body/div/nav/div/div/a[2]")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String title = driver.getTitle();
        assertEquals("ToDo List - Task", title);
	}
	
	@Test
	public void createTaskTest() {
	    driver.get("http://127.0.0.1:5500/html/index.html");
	    driver.findElement(By.linkText("User")).click();
	    driver.findElement(By.linkText("Create")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).sendKeys("Pinky");
	    driver.findElement(By.id("timezone")).click();
	    driver.findElement(By.id("timezone")).sendKeys("UTC");
	    driver.findElement(By.cssSelector(".btn")).click();
	    assertThat(driver.switchTo().alert().getText(), is("User Successfully Created!"));
	    driver.findElement(By.linkText("Task")).click();
	    driver.findElement(By.linkText("Create")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).sendKeys("Take over the world");
	    driver.findElement(By.id("user_id")).click();
	    driver.findElement(By.id("user_id")).sendKeys("1");
	    driver.findElement(By.cssSelector(".btn")).click();
	    assertThat(driver.switchTo().alert().getText(), is("User Successfully Created!"));
	    driver.findElement(By.linkText("Task")).click();
	    driver.findElement(By.linkText("User")).click();
	}
	
	@AfterClass
	public static void shutdown() {
		driver.close();
	}
}
