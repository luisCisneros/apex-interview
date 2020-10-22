package org.example;

import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WebDriverManager {

    private static final Logger logger = LogManager.getLogger();
    public static final String PROPERTIES_PATH = "src/test/resources/config.properties";
    private WebDriver driver;
    private String url;

    public WebDriver getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public WebDriver setUpDriver() {
        logger.info("Properties file located at: {}", PROPERTIES_PATH);
        Properties properties = getProperties(PROPERTIES_PATH);
        url = properties.getProperty("url");
        long timeout = Long.parseLong(properties.getProperty("timeout.in.seconds"));
        boolean maximizeWindow = Boolean.parseBoolean(properties.getProperty("maximize.window"));
        String chromeDriverLocation = properties.getProperty("chrome.driver.location");
        System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        logger.info("Implicitly wait timeout set to {} seconds", timeout);
        driver.manage().deleteAllCookies();
        logger.info("All cookies deleted");
        if (maximizeWindow) {
            driver.manage().window().maximize();
        }
        return driver;
    }

    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String testName = scenario.getName();
            scenario.attach(screenshot, "image/png", testName);
        }
        driver.quit();
    }

    public Properties getProperties(String path) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}
