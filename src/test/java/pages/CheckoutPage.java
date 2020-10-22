package pages;

import org.example.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends PageObject {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "order_name")
    private WebElement name;

    @FindBy(id = "order_address")
    private WebElement address;

    @FindBy(id = "order_email")
    private WebElement email;

    @FindBy(id = "order_pay_type")
    private WebElement payType;

    @FindBy(css = "input[value='Place Order']")
    private WebElement placeOrder;

    public void fillAndPlaceOrder(String name, String address, String email, String payType) {
        this.name.sendKeys(name);
        this.address.sendKeys(address);
        this.email.sendKeys(email);
        Select selectPayType = new Select(this.payType);
        selectPayType.selectByValue(payType);
        placeOrder.click();
    }
}
