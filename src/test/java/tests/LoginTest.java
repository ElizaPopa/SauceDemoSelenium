package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {
    @Test(description = "Verify login with different users", dataProvider = "loginUsers", groups = {"without_login"})
    public void testLoginScenarios(String username, String password, boolean shouldLogin, boolean shouldWait) throws InterruptedException {
        loginSteps(username, password);

        if (shouldLogin) {
            assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                    "User is not redirected to the right page after login");
            List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));
            assertFalse(inventoryItems.isEmpty(), "No item is present after successful login");

            for (WebElement item : inventoryItems) {
                assertTrue(item.isDisplayed(), "Items are not displayed after successful login");
            }
//            assertFalse(driver.getCurrentUrl().contains("inventory.html"),
        } else {
            assertFalse(driver.getCurrentUrl().contains("inventory.html"),
                    "User is redirected to the items page after login with an user that should not see that page");
            if (shouldWait) {
                Thread.sleep(5000);
            }
            assertTrue(isElementPresent(By.cssSelector("h3[data-test='error']")),
                    "No error message after login with an user that should not see items page");
        }
    }

        @DataProvider
        public Object[][] loginUsers () {
            return new Object[][]{
                    {"standard_user", "secret_sauce", true, false},
                    {"locked_out_user", "secret_sauce", false, false},
                    {"problem_user", "secret_sauce", true, false},
                    {"performance_glitch_user", "secret_sauce", true, true}
            };
        }
    }

