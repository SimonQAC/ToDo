package com.qa.todo.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.todo.ToDoApplication;

@SpringBootTest
public class SeleniumTests {

	public static WebDriver driver;
	
	@BeforeClass
	public static void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
//		options.setHeadless(true);
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1280,720));
	}
	
//	@Test
//	public void tempElementTest() {
//	    driver.get("http://127.0.0.1:5500/html/task.html");
//	    String task = driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[2]")).getText();
//	    driver.get("http://127.0.0.1:5500/html/user.html");
//	    String user = driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[2]")).getText();
//	    System.out.println(task + user);
//	}
	
	@Test
	public void createTaskPageTest() {
		driver.get("http://127.0.0.1:5500/html/index.html");
        driver.findElement(By.xpath("/html/body/div/nav/div/div/a[2]")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String title = driver.getTitle();
        assertEquals("ToDo List - Task", title);
	}
	@Test
	public void createUserPageTest() {
		driver.get("http://127.0.0.1:5500/html/index.html");
        driver.findElement(By.xpath("/html/body/div/nav/div/div/a[3]")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String title = driver.getTitle();
        assertEquals("ToDo List - User", title);
	}
	
	@Test
	public void createTaskAndUserTest(){
		SpringApplication.run(ToDoApplication.class);
		WebDriverWait wait = new WebDriverWait(driver,30);
	    driver.get("http://127.0.0.1:5500/html/index.html");
	    driver.findElement(By.linkText("User")).click();
	    driver.findElement(By.linkText("Create")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).sendKeys("Pinky");
	    driver.findElement(By.id("timezone")).click();
	    driver.findElement(By.id("timezone")).sendKeys("UTC");
	    driver.findElement(By.cssSelector(".btn")).click();
		wait.until(ExpectedConditions.alertIsPresent());
	    driver.switchTo().alert().accept();
	    driver.findElement(By.linkText("Task")).click();
	    driver.findElement(By.linkText("Create")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).sendKeys("Take over the world");
	    driver.findElement(By.id("user_id")).click();
	    driver.findElement(By.id("user_id")).sendKeys("1");
	    driver.findElement(By.cssSelector(".btn")).click();
		wait.until(ExpectedConditions.alertIsPresent());
	    driver.switchTo().alert().accept();
	    driver.findElement(By.linkText("Task")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    String task = driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[2]")).getText();
	    driver.findElement(By.linkText("User")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    String user = driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[2]")).getText();
	    System.out.println(task + user);
	    assertEquals("Pinky", user);
	    assertEquals("Take over the world", task);
	}
	
	@Test
	public void updateTaskAndUserTest() {
		SpringApplication.run(ToDoApplication.class);
		WebDriverWait wait = new WebDriverWait(driver,30);
	    driver.get("http://127.0.0.1:5500/html/index.html");
	    driver.findElement(By.linkText("User")).click();
	    driver.findElement(By.linkText("Create")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).sendKeys("Pinky");
	    driver.findElement(By.id("timezone")).click();
	    driver.findElement(By.id("timezone")).sendKeys("UTC");
	    driver.findElement(By.cssSelector(".btn")).click();
		wait.until(ExpectedConditions.alertIsPresent());
	    driver.switchTo().alert().accept();
	    driver.findElement(By.linkText("Task")).click();
	    driver.findElement(By.linkText("Create")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).sendKeys("Take over the world");
	    driver.findElement(By.id("user_id")).click();
	    driver.findElement(By.id("user_id")).sendKeys("1");
	    driver.findElement(By.cssSelector(".btn")).click();
		wait.until(ExpectedConditions.alertIsPresent());
	    driver.switchTo().alert().accept();
	    driver.findElement(By.linkText("Task")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    String task = driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[2]")).getText();
	    driver.findElement(By.linkText("User")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    String user = driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[2]")).getText();
	    System.out.println(task + user);
	    assertEquals("Pinky", user);
	    assertEquals("Take over the world", task);
	    driver.findElement(By.linkText("Task")).click();
	    driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[3]/a")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("Take over the world, with Pinky");
	    driver.findElement(By.xpath("/html/body/div/form/button")).click();
		wait.until(ExpectedConditions.alertIsPresent());
	    driver.switchTo().alert().accept();
	    driver.findElement(By.linkText("User")).click();
	    driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[5]/a")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("The Brain");
	    driver.findElement(By.xpath("/html/body/div/form/button")).click();
		wait.until(ExpectedConditions.alertIsPresent());
	    driver.switchTo().alert().accept();
	    driver.findElement(By.linkText("User")).click();
	    String finaluser = driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[2]")).getText();
	    String finaltask = driver.findElement(By.xpath("/html/body/div/table/thead/tr[2]/td[4]")).getText();
	    System.out.println(finaltask + finaluser);
	    assertEquals("The Brain", finaluser);
	    assertEquals("Take over the world with Pinky", finaltask);
	}
	
	@AfterClass
	public static void shutdown() {
		driver.close();
	}
}
