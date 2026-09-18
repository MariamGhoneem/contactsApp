package base;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Swipes down over the middle of the screen using UiAutomator2's native
     * scroll gesture. The area is inset from the top and bottom edges to avoid
     * the toolbar/status bar and any bottom navigation chrome; it doesn't rely
     * on the UiAutomator "scrollable" accessibility flag, which RecyclerView-based
     * lists often don't set even though they do scroll to a real touch.
     */
    protected void scrollDown() {
        Dimension size = driver.manage().window().getSize();
        Map<String, Object> params = new HashMap<>();
        params.put("left", (int) (size.getWidth() * 0.1));
        params.put("top", (int) (size.getHeight() * 0.2));
        params.put("width", (int) (size.getWidth() * 0.8));
        params.put("height", (int) (size.getHeight() * 0.6));
        params.put("direction", "down");
        params.put("percent", 0.75);
        params.put("speed", 30000);
        driver.executeScript("mobile: scrollGesture", params);
    }

    private WebElement findElement(By locator) {
        return driver.findElement(locator);
    }
}
