package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class DriverManager {

    private static AndroidDriver driver;

    public static AndroidDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    private static AndroidDriver createDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(ConfigReader.get("deviceName"))
                .setPlatformVersion(ConfigReader.get("platformVersion"))
                .setAppPackage(ConfigReader.get("appPackage"))
                .setAppActivity(ConfigReader.get("appActivity"))
                .setNoReset(Boolean.parseBoolean(ConfigReader.get("noReset")));
        options.setCapability("appium:forceAppLaunch", Boolean.parseBoolean(ConfigReader.get("forceAppLaunch")));

        try {
            AndroidDriver newDriver = new AndroidDriver(URI.create(ConfigReader.get("appium.server.url")).toURL(), options);
            newDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            return newDriver;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
