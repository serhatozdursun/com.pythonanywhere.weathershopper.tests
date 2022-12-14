package pages;

import base.BaseTest;
import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.net.IDN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static base.BasePage.getWaitTime;

public class CartPage extends BaseTest {

    public CartPage() {
        var ajax = new AjaxElementLocatorFactory(DriverManager.instance().getDriver(), getWaitTime());
        PageFactory.initElements(ajax, this);
    }

    @FindBy(tagName = "h2")
    private WebElement title;
    @FindBy(id = "total")
    private WebElement total;
    @FindBy(css = "button[type='submit']")
    private WebElement submit;
    @FindBy(tagName = "iframe")
    private WebElement iframe;
    @FindBy(id = "email")
    private WebElement email;
    @FindBy(id = "card_number")
    private WebElement cardNumber;
    @FindBy(id = "cc-exp")
    private WebElement ccExp;
    @FindBy(id = "cc-csc")
    private WebElement csv;
    @FindBy(id = "submitButton")
    private WebElement submitButton;
    @FindBy(id = "billing-zip")
    private WebElement billingZip;

    public String getTitle() {
        return getText(title);
    }

    public List<Map<String, Double>> getCartProducts() {
        var productList = new ArrayList<Map<String, Double>>();
        var tds = DriverManager
                .instance()
                .getDriver()
                .findElements(By.xpath("//tr/td"));
        for (int i = 1; i < tds.size() + 1; i += 2) {
            var productName = DriverManager
                    .instance()
                    .getDriver()
                    .findElement(By.xpath("(//tr/td)[" + i + "]"))
                    .getText();
            var priceText = DriverManager
                    .instance()
                    .getDriver()
                    .findElement(By.xpath("(//tr/td)[" + (i + 1) + "]"))
                    .getText();
            var price = Double.parseDouble(priceText);
            var product = new HashMap<String, Double>();
            product.put(productName, price);
            productList.add(product);
        }
        return productList;
    }

    public String getTotal() {
        return getText(total);
    }

    public void clickSubmit() {
        clickElement(submit);
        switchToFrame(iframe);
    }

    public void typeEmail(String emailText) {
        sendKeys(email, emailText);
    }

    public void typeCardNumber(String cardNumberText) {
        sendKeysCharByChar(cardNumber, cardNumberText);
    }

    public void typeCcExp(String ccExpText) {
        sendKeysCharByChar(ccExp, ccExpText);
    }

    public void typeCsv(String csvText) {
        sendKeys(csv, csvText);
    }

    public void clickSubmitButton() {
        clickElement(submitButton);
        waitInvisibleOfElement(submitButton);
        switchToDefaultContent();
    }

    public void typeBillingZip(String zip) {
        sendKeys(billingZip, zip);
    }
}
