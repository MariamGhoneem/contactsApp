package base;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class PageBase {

    protected AndroidDriver driver;

    public PageBase(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected void click(By locator) {
        findElement(locator).click();
    }

    protected void type(By locator, String text) {
        findElement(locator).sendKeys(text);
    }

    protected boolean isDisplayed(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    private WebElement findElement(By locator) {
        return driver.findElement(locator);
    }
}
