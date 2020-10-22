package pages;

import org.example.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PageToolkit extends PageObject {

    public PageToolkit(WebDriver driver) {
        super(driver);
    }

    public WebElement getButtonByValue(String value) {
        String locator = String.format("input[value='%s']", value);
        return driver.findElement(By.cssSelector(locator));
    }

    public void performAddPuppiesToTheShoppingCart(List<String> puppies) {
        int lastIndex = puppies.size() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            WebElement viewDetails = new LandingPage(driver).getPuppyViewDetailsButton(puppies.get(i));
            if (viewDetails != null) {
                viewDetails.click();
            } else {
                String message = String.format("Could not click on View Details. Verify that [%s] is a valid puppy name", puppies.get(i));
                throw new RuntimeException(message);
            }
            new PuppyDetailsPage(driver).getAdoptMe().click();
            if (i != lastIndex) {
                new ShoppingCartPage(driver).getAdoptAnotherPuppy().click();
            }
        }
    }
}
