package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public final static String USERNAME_LOC_ID = "username-in";
    public final static String PASSWORD_LOC_ID = "password-in";
    public final static String SUBMIT_BTN_LOC_CSS = ".btn.btn-primary";

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void inputUsername(String username) {
        webDriver.findElement(By.id(USERNAME_LOC_ID)).sendKeys(username);
    }

    public void inputPassword(String password) {
        webDriver.findElement(By.id(PASSWORD_LOC_ID)).sendKeys(password);
    }

    public void login(String username, String password) throws InterruptedException {
        inputUsername(username);
        inputPassword(password);
        webDriver.findElement(By.cssSelector(SUBMIT_BTN_LOC_CSS)).click();
    }


}
