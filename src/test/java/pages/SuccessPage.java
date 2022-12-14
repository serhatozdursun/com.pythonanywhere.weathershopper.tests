package pages;

import base.BaseTest;
import driver.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static base.BasePage.getWaitTime;

public class SuccessPage extends BaseTest {
    public SuccessPage() {
        var ajax = new AjaxElementLocatorFactory(DriverManager.instance().getDriver(), getWaitTime());
        PageFactory.initElements(ajax, this);
        waitUntilPageLoad();
    }

    @FindBy(tagName = "h2")
    private WebElement title;

    @FindBy(css = ".text-justify")
    private WebElement message;
    public String getTitle() {
        return getText(title);
    }

    public String getMessage() {
        return getText(message);
    }
}
