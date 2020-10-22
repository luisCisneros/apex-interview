package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PuppiesSteps {

    private static final Logger logger = LogManager.getLogger();
    private final WebDriverManager webDriverManager;
    private final WebDriver driver;
    private LandingPage landingPage;
    private PuppyDetailsPage puppyDetailsPage;
    private ShoppingCartPage shoppingCartPage;
    private PageToolkit pageToolkit;
    private List<String> puppies;
    private String homePageTitle;

    public PuppiesSteps(WebDriverManager webDriverManager) {
        this.webDriverManager = webDriverManager;
        driver = webDriverManager.getDriver();
    }

    @Given("user is on puppies.herokuapp landing page")
    public void userIsOnPuppiesHerokuappLandingPage() {
        driver.get(webDriverManager.getUrl());
        homePageTitle = driver.getTitle();
    }


    @Given("(I )select the puppy named {string} by clicking on View Details")
    public void userSelectsPuppyByClickingOn(String puppy) {
        landingPage = new LandingPage(driver);
        WebElement viewDetails = landingPage.getPuppyViewDetailsButton(puppy);
        if (viewDetails != null) {
            viewDetails.click();
        } else {
            String message = String.format("Could not click on View Details. Verify that [%s] is a valid puppy name", puppy);
            throw new RuntimeException(message);
        }
    }

    @And("navigate to Checkout page")
    public void navigateToCheckoutPage() {
        puppyDetailsPage = new PuppyDetailsPage(driver);
        puppyDetailsPage.getAdoptMe().click();
        shoppingCartPage = new ShoppingCartPage(driver);
        shoppingCartPage.getCompleteTheAdoption().click();
    }

    @When("I place the order with the following details:")
    public void iEnterTheFollowingDetails(Map<String, String> checkoutDetails) {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillAndPlaceOrder(
                checkoutDetails.get("name"),
                checkoutDetails.get("address"),
                checkoutDetails.get("email"),
                checkoutDetails.get("pay_type")
        );
    }

    @Then("I should be redirected to the homepage")
    public void userShouldBeRedirectedToHomepage() {
        assertEquals(driver.getTitle(), homePageTitle);
    }

    @And("the message {string} should be displayed")
    public void theMessageShouldBeDisplayed(String expectedMessage) {
        landingPage = new LandingPage(driver);
        String actualMessage = landingPage.getNotice().getText();
        logger.debug("Actual message: {}", actualMessage);
        assertEquals(expectedMessage, actualMessage);
    }

    @Then("The following menu items should be displayed on the Landing page:")
    public void theFollowingMenuItemsShouldBeDisplayed(List<String> expectedMenuItems) {
        LandingPage landingPage = new LandingPage(driver);
//        List<WebElement> navMenuItems = landingPage.getNavigationMenuLinks();
//        List<String> actualMenuItems = new ArrayList<>();
//        navMenuItems.forEach(menuItem -> actualMenuItems.add(menuItem.getText()));
//        Assert.assertTrue(actualMenuItems.equals(expectedMenuItems));
        for (String item : expectedMenuItems) {
            WebElement menuItem = landingPage.getNavigationMenuItemByTitle(item);
            logger.debug("Menu item: {}", menuItem.getText());
            assertTrue(menuItem.isDisplayed());
        }
    }

//    @And("^click on \"([^\"]*)\" on \"([^\"]*)\" page$")
    @And("(I )click on {string} on {string} page")
    public void clickOnButtonOnPage(String title, String page) {
        pageToolkit = new PageToolkit(driver);
        pageToolkit.getButtonByValue(title).click();
        if (title.equalsIgnoreCase("Change your mind")) {
            logger.debug("Inside IF block");
            driver.switchTo().alert().accept();
            driver.switchTo().alert().accept();
        }
    }

    @Given("I want to adopt the following puppies:")
    public void iWantToAdoptTheFollowingPuppies(List<String> puppies) {
        this.puppies = new ArrayList<>(puppies);
    }

    @When("I add them to my shopping cart")
    public void iAddThemToMyShoppingCart() {
        pageToolkit = new PageToolkit(driver);
        pageToolkit.performAddPuppiesToTheShoppingCart(puppies);
    }

    @Then("total sum of the cost of the puppies must be displayed on the Shopping Cart page")
    public void totalSumOfTheCostOfThePuppiesMustBeDisplayedOnTheShoppingCartPage() {
        shoppingCartPage = new ShoppingCartPage(driver);
        List<WebElement> puppyPrices = shoppingCartPage.getPuppyPrices();
        double sum = 0;
        for (WebElement element : puppyPrices) {
            String cleanPrice = element.getText().replace("$", "");
            sum += Double.parseDouble(cleanPrice);
        }
        logger.debug("Sum was: {}", sum);
        String puppiesSum = String.format("$%.2f", sum);
        WebElement actualTotal = shoppingCartPage.getTotalAmount();
        assertTrue(actualTotal.isDisplayed());
        assertEquals(puppiesSum, actualTotal.getText());
    }

    @Then("At most {int} records should be displayed on each puppy list")
    public void atMostRecordsShouldBeDisplayedOnEachPuppyList(int maxNumberOfRecords) {
        landingPage = new LandingPage(driver);
        boolean isNextPageEnabled = false;
        do {
            assertTrue(landingPage.getPuppiesRecords().size() <= maxNumberOfRecords);
            isNextPageEnabled = landingPage.getNextPageList().size() > 0;
            if (isNextPageEnabled) {
                landingPage.getNextPage().click();
            }
        } while (isNextPageEnabled);
    }

    @When("I navigate among all the views of the list")
    public void iNavigateAmongAllTheViewsOfTheList() {
    }


    @Then("each puppy record should display the name, breed and sex")
    public void eachPuppyRecordShouldDisplayTheNameBreedAndSex() {
        landingPage = new LandingPage(driver);
        boolean isNextPageEnabled = false;
        do {
            List<WebElement> puppiesRecords = landingPage.getPuppiesRecords();
            for (WebElement record : puppiesRecords) {
                WebElement name = record.findElement(By.cssSelector(".name h3"));
                WebElement breed = record.findElement(By.xpath("//div[@class='details']//h4[1]"));
                WebElement sex = record.findElement(By.xpath("//div[@class='details']//h4[2]"));
                logger.debug("[Name: {}]\t[Breed: {}]\t[Sex: {}]", name.getText(), breed.getText(), sex.getText());
                assertTrue(name.isDisplayed());
                assertTrue(breed.isDisplayed());
                assertTrue(sex.isDisplayed());
            }
            isNextPageEnabled = landingPage.getNextPageList().size() > 0;
            if (isNextPageEnabled) {
                landingPage.getNextPage().click();
            }
        } while (isNextPageEnabled);
    }
}
