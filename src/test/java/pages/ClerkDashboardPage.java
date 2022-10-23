package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClerkDashboardPage extends BasePage{

    public final static String TITLE_LOC_CSS = "body > div.row.justify-content-md-center > div.col-md > h1";
    public final static String CSV_UPLOAD_TITLE_LOC_CSS = "body > div.container > div:nth-child(2) > label";
    public final static String CSV_UPLOAD_INPUT_LOC_CSS = "#upload-csv-file";
    public final static String CSV_UPLOAD_CREATE_BTN_LOC_CSS = "body > div.container > div.p-2.row.text-start.align-items-xl > div:nth-child(3) > button";
    public final static String CSV_UPLOAD_RESULT_TEXT_LOC_XPATH = "//*[@id=\"notification-block\"]/div/h3";

    public ClerkDashboardPage(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement getTitleElement() {
        return webDriver.findElement(By.cssSelector(TITLE_LOC_CSS));
    }

    public WebElement getCsvUploadTitleElement() {
        return webDriver.findElement(By.cssSelector(CSV_UPLOAD_TITLE_LOC_CSS));
    }

    public WebElement getCsvUploadInputElement() {
        return webDriver.findElement(By.cssSelector(CSV_UPLOAD_INPUT_LOC_CSS));
    }

    public WebElement getCsvUploadCreateBtn() {
        return webDriver.findElement(By.cssSelector(CSV_UPLOAD_CREATE_BTN_LOC_CSS));
    }

    public WebElement getCsvUploadResultTextElement() {
        return webDriver.findElement(By.xpath(CSV_UPLOAD_RESULT_TEXT_LOC_XPATH));
    }
}
