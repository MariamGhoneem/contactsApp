package base;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.PageFactory;

public class PageBase {

    protected AndroidDriver driver;

    public PageBase(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
