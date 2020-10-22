package pages;

import org.example.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ShoppingCartPage extends PageObject {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input[value='Complete the Adoption']")
    private WebElement completeTheAdoption;

    @FindBy(css = "input[value='Adopt Another Puppy']")
    private WebElement adoptAnotherPuppy;

    @FindBy(css = "td.item_price h2")
    private List<WebElement> puppyPrices;

    @FindBy(css = "td.total_cell h2")
    private WebElement totalAmount;

    public WebElement getCompleteTheAdoption() {
        return completeTheAdoption;
    }

    public WebElement getAdoptAnotherPuppy() {
        return adoptAnotherPuppy;
    }

    public List<WebElement> getPuppyPrices() {
        return puppyPrices;
    }

    public WebElement getTotalAmount() {
        return totalAmount;
    }
}
