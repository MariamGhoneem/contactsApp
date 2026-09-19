# Contacts App Test

UI automation for the built-in Android **Contacts** app (`com.google.android.contacts`), built with Appium, TestNG, and Maven. Currently covers a single scenario: creating a contact with valid data and verifying it's saved.

## Tech stack

- Java 17
- Appium (UiAutomator2 driver) + Appium Java client 10
- TestNG
- Maven (Surefire)
- Allure (`allure-testng`) for reporting
- Datafaker for generating random contact data

## Project structure

```
src/main/java
├── base/           # DriverManager (Appium session), PageBase
├── models/         # Contact
├── pages/          # Page objects: ContactsListPage, CreateContactPage, ContactDetailsPage
└── utils/          # ConfigReader, ContactDataGenerator

src/test/java
├── base/           # TestBase (@BeforeMethod/@AfterMethod driver lifecycle)
└── tests/          # CreateContactTest

src/test/resources/
├── config.properties     # Appium server URL + device/app capabilities
└── allure.properties     # Allure results output directory

testng.xml                # Suite definition (scans the `tests` package)
```

## Framework & library justification

- **Datafaker** — used to generate contact data (name, phone, etc.) instead of hardcoding it, so the test's result is trustworthy on repeat runs: hardcoded data means a rerun collides with the contact saved by the previous run (same name already existing), which can mask a real failure or fail a test that should pass.

## Prerequisites

- JDK 17 and Maven
- Node/npm, with Appium installed and the `uiautomator2` driver:
  ```
  npm install -g appium
  appium driver install uiautomator2
  ```
- Android platform-tools (`adb`) on your `PATH`
- A connected Android device or emulator with **USB debugging enabled** and the Google Contacts app installed
  - Verify with `adb devices` — it should list your device

## Configuration

Edit `src/test/resources/config.properties` to match your device:

| Key | Meaning |
|---|---|
| `appium.server.url` | URL of the running Appium server (default `http://127.0.0.1:4723`) |
| `deviceName` | Device identifier — for a real device, use the value from `adb devices` (its serial/UDID); for an emulator, its AVD name (e.g. `emulator-5554`) |
| `platformVersion` | Android version of that device, e.g. `16` — check with `adb shell getprop ro.build.version.release` |
| `appPackage` / `appActivity` | The Contacts app's package and launch activity |
| `noReset` | Keep app data between runs (`true` avoids reinstalling/clearing data) |
| `forceAppLaunch` | Forces the app to the foreground even if its process is already running in the background — without this, `noReset=true` can skip launching the app entirely if it was previously opened, leaving it backgrounded instead of on the contacts list |

## Running the tests

1. Start the Appium server:
   ```
   appium
   ```
2. Make sure your device/emulator is connected (`adb devices`) and unlocked.
3. Run the suite:
   ```
   mvn test
   ```
   Or from IntelliJ: right-click `testng.xml` → **Run 'ContactsAppSuite'** (the Appium server and device must already be up; the run reads `config.properties` the same way the CLI run does).

## Test reports

Every run (`mvn test` or from IntelliJ) writes raw Allure results to `target/allure-results`. To turn those into a report you can actually look at:

```
mvn allure:serve
```

This builds the report and starts a local server, then prints a line like:
```
Server started at <http://127.0.1.1:PORT/>. Press <Ctrl+C> to exit
```
Open that URL in a browser. It tries to auto-open one for you, but on a machine without a desktop session it can't (`Browse operation is not supported on your platform`) — that's fine, just use the printed URL. Stop the server with `Ctrl+C` when done.

If you're running from IntelliJ, `AllureReportServer` (`@AfterSuite` in `src/test/java/tests`) does this automatically at the end of the suite — check the run console for the same `Server started at ...` line instead of running the command yourself.

Only need a static report file instead of a live server? Use `mvn allure:report` — it's written to `target/site/allure-maven-plugin/index.html`.

## Known limitations

- `deviceName`/`platformVersion` in `config.properties` are pinned to a specific device — update them if you run against a different device or emulator.
- Contacts created by the test are not cleaned up afterward (`noReset=true`), so repeated runs will accumulate test contacts on the device.
