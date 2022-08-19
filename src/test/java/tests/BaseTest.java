package tests;

import driver.WebdriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected ChromeDriver driver = null;

    @BeforeMethod(onlyForGroups = {"with_login"})
    public void beforeMethodWithLogin() {
        driver = WebdriverConfig.getChromeDriver();
        driver.get("https://www.saucedemo.com/");
        loginSteps("standard_user", "secret_sauce");

    }

    @BeforeMethod(onlyForGroups = {"without_login"})
    public void beforeMethodWithoutLogin() {
        driver = WebdriverConfig.getChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void loginSteps(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isElementPresent(WebElement element, By locator) {
        try {
            element.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
