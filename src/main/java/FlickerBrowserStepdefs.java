import common.FlickerJsonParser;
import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import pages.MainPage;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(Cucumber.class)
@CucumberOptions(  monochrome = true,
        tags = "@tags",
        features = "src/test/resources/",
        format = { "pretty","html: cucumber-html-reports",
                "json: cucumber-html-reports/cucumber.json" },
        dryRun = false,
        glue = "fr.tlasnier.cucumber" )

public class FlickerBrowserStepdefs {
    IOSDriver driver;
    MainPage mainPage;

    String jsonUrl = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=";

    /**
     * Set up parameters and connect to Appium server. Also get a hook to applications main page for testing.
     * @throws MalformedURLException
     */
    @Before
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "iPhone Simulator");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "10.3");
        capabilities.setCapability("app", "https://s3-eu-west-1.amazonaws.com/backup.red-mamba.com/razno/FlickerBrowser.ipa");
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        mainPage = new MainPage(driver);
    }

    /**
     * Release objects when done testing.
     */
    @After
    public void teardown() {
        driver.quit();
    }

    @Given("^I enter London as my search string$")
    public void i_enter_London_as_my_search_string() throws Throwable {
        mainPage.enterString("London");
    }

    @And("^I press Search button$")
    public void clickSearchButton() throws Throwable {
        mainPage.clickSearchButton();
    }

    @Then("^I should get latest pictures from London$")
    public void checkResults(int resultsCount) throws Throwable {
        //Evaluate results:
        FlickerJsonParser flickerJsonParser = new FlickerJsonParser(jsonUrl + "London");
        String[] items = flickerJsonParser.returnItems();


        String[] results = mainPage.returnResults();

        Assert.assertEquals(items.length, results.length);

        for (int i=0; i<results.length; i++) {
            Assert.assertEquals(results[i], items[i]);
        }
    }
}
