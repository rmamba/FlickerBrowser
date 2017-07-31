package test;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.MainPage;
import common.FlickerJsonParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FlickerBrowserTests {

    IOSDriver driver;
    MainPage mainPage;

    String jsonUrl = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=";

    @Before
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "iPhone Simulator");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "10.3");
        capabilities.setCapability("app", "https://s3-eu-west-1.amazonaws.com/backup.red-mamba.com/razno/FlickerBrowser.ipa");
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        mainPage = new MainPage(driver);
//        searchResults = new SearchResultsPage(driver);
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void evaluateLondonSearchResults() throws InterruptedException {
        //Enter search word: 'London'
        mainPage.searchFor("London");

        //Evaluate results:
        FlickerJsonParser flickerJsonParser = new FlickerJsonParser(jsonUrl + "London");
        String[] items = flickerJsonParser.returnItems();

        Thread.sleep(1000);

        String[] results = mainPage.returnResults();

        Assert.assertEquals(items.length, results.length);

        for (int i=0; i<results.length; i++) {
            Assert.assertEquals(results[i], items[i]);
        }
    }
}
