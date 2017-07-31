package pages;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Helper class to handle applications main page.
 */
public class MainPage {
    IOSDriver driver;

    @FindBy(xpath = "//XCUIElementTypeApplication[@name=\"FlickrBrowser-cal\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSearchField")
    MobileElement searchForm;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Search\"]")
    MobileElement searchButton;

    @FindBy(xpath = "//XCUIElementTypeApplication[@name=\"FlickrBrowser-cal\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeCollectionView")
    MobileElement collectionView;

    /**
     * Class initialisation with the provided IOSDriver. Will also initialise MobileElements on the application main page.
     * @param driver
     */
    public MainPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
    }

    /**
     * Put string in search box.
     * @param searchString String to search for
     */
    public void enterString(String searchString) {
        searchForm.sendKeys(searchString);
    }

    /**
     * Click Search button
     */
    public void clickSearchButton() {
        searchButton.click();
    }

    /**
     * This method enters text into search box on device and clicks Search button.
     * @param searchString String to search for
     */
    public void searchFor(String searchString) {
        this.enterString(searchString);
        this.clickSearchButton();
    }

    /**
     * Parses cells and extracts titles under images so we can test them
     * @return An array of Strings representing titles of displayed table objects
     */
    public String[] returnResults() {
        //get list of elements
        List<MobileElement> cells = collectionView.findElementsByClassName("XCUIElementTypeCell");
        String[] ret = new String[cells.size()];

        //for every cell element get it's title and store it in array
        int pos = 0;
        for (MobileElement cell : cells) {
            MobileElement stringCell = cell.findElementByClassName("XCUIElementTypeStaticText");
            ret[pos++] = stringCell.getAttribute("value");
        }

        //return our array of titles
        return ret;
    }
}
