package pages;

import org.example.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LandingPage extends PageObject {

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public static final String NEXT_PAGE_LOCATOR = "a.next_page";

    @FindBy(css = "#navlist li a")
    private List<WebElement> navigationMenuLinks;

    @FindBy(id = "notice")
    private WebElement notice;

    @FindBy(css = NEXT_PAGE_LOCATOR)
    private WebElement nextPage;

    @FindBy(css = NEXT_PAGE_LOCATOR)
    private List<WebElement> nextPageList;

    @FindBy(css = "div[class^=list_line]")
    private List<WebElement> puppiesRecords;

    public List<WebElement> getNavigationMenuLinks() {
        return navigationMenuLinks;
    }

    public WebElement getNotice() {
        return notice;
    }

    public WebElement getNextPage() {
        return nextPage;
    }

    public List<WebElement> getPuppiesRecords() {
        return puppiesRecords;
    }

    public List<WebElement> getNextPageList() {
        return nextPageList;
    }

    public WebElement getNavigationMenuItemByTitle(String title) {
        String path = String.format("//a[text()='%s']", title);
        return driver.findElement(By.xpath(path));
    }

    public WebElement getPuppyViewDetailsButton(String name) {
        List<WebElement> puppyList = driver.findElements(By.className("puppy_list"));
        for (WebElement element : puppyList) {
            String header = element.findElement(By.tagName("h3")).getText();
            if (header.equals(name)) {
                return element.findElement(By.tagName("input"));
            }
        }
        return null;
    }

}
