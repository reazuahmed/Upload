package com.inetbanking.testCases;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inetbanking.utilities.ReadConfig;

public class BaseClass {

	ReadConfig readConfig = new ReadConfig();
	public String baseURL = readConfig.getApplicationURL();
	public String username = readConfig.getUserName();
	public String password = readConfig.getPassword();

	public static WebDriver driver;

	public static Logger logger;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br) {
		logger = Logger.getLogger("ebanking");
		PropertyConfigurator.configure("log4j.properties");

		if (br.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", readConfig.getChromePath());
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
			driver = new ChromeDriver(chromeOptions);
		} else if (br.equals("firefox")) {

			System.setProperty("webdriver.gecko.driver", readConfig.getFirefoxPath());
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			driver = new FirefoxDriver(firefoxOptions);
		} else if (br.equals("ie")) {
			System.setProperty("webdriver.ie.driver", readConfig.getIEPath());
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken...");
	}

	public String randomestring() {
		String generatedString = RandomStringUtils.randomAlphabetic(8);
		return (generatedString);

	}

	public static String randomNumber() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return (generatedString2);
	}
}
