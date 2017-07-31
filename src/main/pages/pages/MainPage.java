package pages;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import sun.applet.Main;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPage {
    IOSDriver driver;

    @FindBy(xpath = "//XCUIElementTypeApplication[@name=\"FlickrBrowser-cal\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSearchField")
    MobileElement searchForm;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Search\"]")
    MobileElement searchButton;

    @FindBy(xpath = "//XCUIElementTypeApplication[@name=\"FlickrBrowser-cal\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeCollectionView")
    MobileElement collectionView;

    public MainPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
    }

    public void searchFor(String searchString) {
        searchForm.sendKeys(searchString);
        searchButton.click();
    }

    public String[] returnResults() {
        List<MobileElement> cells = collectionView.findElementsByClassName("XCUIElementTypeCell");
        String[] ret = new String[cells.size()];

        int pos = 0;
        for (MobileElement cell : cells) {
            MobileElement stringCell = cell.findElementByClassName("XCUIElementTypeStaticText");
            ret[pos++] = stringCell.getAttribute("value");
        }

        return ret;
    }
}
