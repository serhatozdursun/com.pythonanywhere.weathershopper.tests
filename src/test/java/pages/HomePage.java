package pages;

import base.BaseTest;
import driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static base.BasePage.getWaitTime;

public class HomePage extends BaseTest {
    private final static Logger log = LogManager.getLogger(HomePage.class);

    private static String temperatureValue;

    public HomePage() {
        var ajax = new AjaxElementLocatorFactory(DriverManager.instance().getDriver(), getWaitTime());
        PageFactory.initElements(ajax, this);
        temperatureValue=null;
    }


    @FindBy(id = "temperature")
    private WebElement temperature;

    @FindBy(xpath = "//button[text()='Buy moisturizers']")
    private WebElement moisturizers;

    @FindBy(xpath = "//button[text()='Buy sunscreens']")
    private WebElement sunscreens;

    public void getTemperature(){
        temperatureValue = getText(temperature);
    }

    public void clickMoisturizers(){
        clickElement(moisturizers);
    }

    public void clickSunscreens(){
       clickElement(sunscreens);
    }


    public static String getCurrentTemperature() {
        return temperatureValue;
    }
}
