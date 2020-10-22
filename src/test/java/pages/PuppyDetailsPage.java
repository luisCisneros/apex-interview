package pages;

import org.example.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PuppyDetailsPage extends PageObject {

    public PuppyDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input[type='submit']")
    private WebElement adoptMe;

    public WebElement getAdoptMe() {
        return adoptMe;
    }
}
