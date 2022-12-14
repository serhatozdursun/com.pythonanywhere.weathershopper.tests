package pages;

import base.BaseTest;
import driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static base.BasePage.getWaitTime;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductPage extends BaseTest {
    private final static Logger log = LogManager.getLogger(ProductPage.class);
    private static List<Map<String, Double>> addedProducts = new ArrayList<>();

    public ProductPage() {
        var ajax = new AjaxElementLocatorFactory(DriverManager.instance().getDriver(), getWaitTime());
        PageFactory.initElements(ajax, this);
        waitUntilPageLoad();
    }

    @FindBy(css = "h2")
    private WebElement h2Title;

    @FindBy(css = ".text-center")
    private List<WebElement> products;

    @FindBy(css = "button[onclick='goToCart()']")
    private WebElement cart;

    public String getTitle() {
        return getText(h2Title);
    }

    public void clickLeastExpensive(String titleText) {
        var leasExpensivePrice = 1000000.00;
        WebElement leasExpensiveProduct = null;
        var leasExpensiveTitle = "";
        double productPrice = 0;
        for (int i = 1; i < products.size() + 1; i++) {
            var title = DriverManager
                    .instance()
                    .getDriver()
                    .findElement(By.xpath("(//p[contains(@class,'font-weight-bold')])[" + i + "]"))
                    .getText();
            var priceText = DriverManager
                    .instance()
                    .getDriver()
                    .findElement(By.xpath("(//p[contains(text(),'Price')])[" + i + "]"))
                    .getText()
                    .replaceAll("Price: Rs. |Price: ", "");
            productPrice = Double.parseDouble(priceText);

            if (title.toLowerCase().contains(titleText.toLowerCase()) && leasExpensivePrice > productPrice) {
                leasExpensivePrice = productPrice;
                leasExpensiveTitle = title;
                leasExpensiveProduct = DriverManager
                        .instance()
                        .getDriver()
                        .findElement(By.xpath("(//button[text()='Add'])[" + i + "]"));
            }
        }
        clickWithJavaScript(leasExpensiveProduct);
        var addedProduct = new HashMap<String, Double>();
        addedProduct.put(leasExpensiveTitle, leasExpensivePrice);
        addedProducts.add(addedProduct);
    }

    public void clickCart() {
        clickElement(cart);
    }

    public static List<Map<String, Double>> getAddedProducts() {
        return addedProducts;
    }

}
