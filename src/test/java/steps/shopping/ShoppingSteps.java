package steps.shopping;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.eo.Do;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SuccessPage;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.HomePage.getCurrentTemperature;

public class ShoppingSteps {

    private static String productPageName;
    private String currentTemp;

    private HomePage homePage = new HomePage();
    private ProductPage productPage = new ProductPage();

    private CartPage cartPage = new CartPage();

    private SuccessPage successPage = new SuccessPage();

    @Given("Random temperature")
    public void random_temperature() {
        homePage.getTemperature();
    }

    @When("Click the shopping card according temperature")
    public void click_the_shopping_card_according_temperature() {
        var temp = getCurrentTemperature()
                .replaceAll("℃|°C", "").strip();
        var tempValue = Integer.parseInt(temp);
        if (tempValue > 34) {
            homePage.clickSunscreens();
            currentTemp = "above 34";
        } else if (tempValue < 19) {
            homePage.clickMoisturizers();
            currentTemp = "below 19";
        } else
            throw new IllegalArgumentException("Temperature out of the range " + getCurrentTemperature());

    }

    @Then("Check the Page is the valid page according to temperature")
    public void check_the_page_is_the_valid_page_according_to_temperature(Map<String, List<String>> conditions) {
        var expectedPage = conditions.get(currentTemp).get(0);
        var actualPage = productPage.getTitle();
        assertEquals(expectedPage, actualPage);
        productPageName = actualPage;
    }

    @Then("then add product to card according to product adding conditions")
    public void thenAddProductToCardAccordingToProductAddingConditions() {
        if (productPageName.equals("Moisturizers")) {
            productPage.clickLeastExpensive("Aloe");
            productPage.clickLeastExpensive("almond");

        } else if (productPageName.equals("Sunscreens")) {
            productPage.clickLeastExpensive("SPF-30");
            productPage.clickLeastExpensive("SPF-30");
        } else
            throw new IllegalArgumentException("Unexpected page " + productPage);

    }

    @And("Click on cart")
    public void clickOnCart() {
        productPage.clickCart();
    }

    @Then("Verify that the shopping cart looks correct")
    public void verifyThatTheShoppingCartLooksCorrect() {
        assertEquals("Checkout", cartPage.getTitle());
        var addedCardList = productPage.getAddedProducts();
        var cartProductList = cartPage.getCartProducts();
        assertEquals(addedCardList, cartProductList);
        var totalText = cartPage.getTotal().replace("Total: Rupees ", "");
        var total = Double.parseDouble(totalText);
        var expectedTotal = cartProductList
                .stream()
                .map(i -> i.entrySet().stream().map(m -> m.getValue()).reduce(Double::sum))
                .map(i -> i.get())
                .reduce(Double::sum)
                .get();
        assertEquals(expectedTotal, total);
    }

    @Then("fill out your payment details and submit the form")
    public void fillOutYourPaymentDetailsAndSubmitTheForm(List<List<String>> paymentInfo) {
        cartPage.clickSubmit();
        cartPage.typeEmail(paymentInfo.get(0).get(0));
        cartPage.typeCardNumber(paymentInfo.get(0).get(1));
        cartPage.typeCsv(paymentInfo.get(0).get(2));
        cartPage.typeCcExp(paymentInfo.get(0).get(3).replace("/", ""));
        cartPage.typeBillingZip(paymentInfo.get(0).get(4));

        cartPage.clickSubmitButton();

    }

    @Then("{string} title should display on the page")
    public void messageShouldDisplayOnThePage(String message) {
        var title = successPage.getTitle();
        assertEquals(message, title);
    }

    @Then("An information message should be displayed on page")
    public void anInformationMessageShouldBeDisplayedOnPage() {
        var pageMessage = successPage.getMessage();
        assertEquals("Your payment was successful. You should receive a follow-up call from our sales team.", pageMessage);
    }
}
