package tests;

import driver.WebdriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class OrderTest extends BaseTest {
    @Test(description = "Verify that adding elements to cart works as expected", groups = {"with_login"})
    public void testAddToCart() {
        WebElement shoppingCart = driver.findElement(By.cssSelector("a.shopping_cart_link"));
        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));
        WebElement firstItem = inventoryItems.get(0);
        String firstItemTitle = firstItem.findElement(By.cssSelector(".inventory_item_name")).getText();
        String firstItemPriceTag = firstItem.findElement(By.cssSelector(".inventory_item_price")).getText();
        WebElement firstItemButton = firstItem.findElement(By.id("add-to-cart-sauce-labs-backpack"));

        assertEquals(firstItemButton.getText().toLowerCase(), "add to cart",
                "Add to cart button is incorrect before adding an item to cart");
        assertFalse(isElementPresent(By.cssSelector("span.shopping_cart_badge")),
                "Shopping cart is not empty");
        firstItemButton.click();
        assertEquals(firstItem.findElement(By.id("remove-sauce-labs-backpack")).getText().toLowerCase(), "remove",
                "Add to cart button is incorrect after adding an item to cart");
        assertTrue(isElementPresent(By.cssSelector("span.shopping_cart_badge")),
                "Shopping cart is empty, no product was added");
        assertEquals(driver.findElement(By.cssSelector("span.shopping_cart_badge")).getText(), "1",
                "Number of elements inside the shopping cart badge is incorrect");

        shoppingCart.click();
        assertTrue(driver.getCurrentUrl().contains("cart.html"),
                "User is not on the cart page after navigating to it");
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(cartItems.size(), 1, "Number of cart items is incorrect");
        String cartItemName = cartItems.get(0).findElement(By.className("inventory_item_name")).getText();
        assertEquals(cartItemName, firstItemTitle, "Wrong item was added to cart");
        String cartItemPrice = cartItems.get(0).findElement(By.className("inventory_item_price")).getText();
        assertEquals(cartItemPrice, firstItemPriceTag, "Wrong price");

        assertTrue(isElementPresent(cartItems.get(0), By.id("remove-sauce-labs-backpack")),
                "Remove button is not present");
        assertTrue(isElementPresent(By.id("continue-shopping")),
                "Continue shopping button is not present");
        assertTrue(isElementPresent(By.id("checkout")), "Checkout button is not present");
    }

}
