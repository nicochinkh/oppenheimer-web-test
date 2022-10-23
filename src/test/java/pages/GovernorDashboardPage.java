package pages;

import module.WorkingClassHero;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class GovernorDashboardPage extends BasePage {

    public final static String TITLE_LOC_CSS = "body > div.row.justify-content-md-center > div.col-md > h1";
    public final static String LIST_ALL_BTN_CSS = "body > div:nth-child(3) > div:nth-child(2) > button";
    public final static String HERO_TABLE_TR_XPATH = "//*[@id=\"search-all-table\"]/tbody/tr";
    private final static String HERO_TABLE_TD_XPATH_PRE = "//*[@id=\"search-all-table\"]/tbody/tr";
    private final static String SEARCH_INPUT_LOC_CSS = "#search-all-table_filter > label > input";

    public GovernorDashboardPage(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement getTitleElement() {
        return webDriver.findElement(By.cssSelector(TITLE_LOC_CSS));
    }

    public WebElement getListAllButton() {
        return webDriver.findElement(By.cssSelector(LIST_ALL_BTN_CSS));
    }

    public List<WorkingClassHero> getHeroSearchAllTableContent() {
        List<WebElement> rows = webDriver.findElements(By.xpath(HERO_TABLE_TR_XPATH));
        List<WorkingClassHero> heroList = new ArrayList<>();
        for (int i = 1; i <= rows.size(); i++) {
            WorkingClassHero workingClassHero = new WorkingClassHero();
            workingClassHero.setNatid(webDriver.findElement(By.xpath(HERO_TABLE_TD_XPATH_PRE + "[" + i + "]" + "/td[" + 1 + "]")).getText());
            workingClassHero.setName(webDriver.findElement(By.xpath(HERO_TABLE_TD_XPATH_PRE + "[" + i + "]" + "/td[" + 2 + "]")).getText());
            workingClassHero.setGender(webDriver.findElement(By.xpath(HERO_TABLE_TD_XPATH_PRE + "[" + i + "]" + "/td[" + 3 + "]")).getText());
            workingClassHero.setBirthDate(webDriver.findElement(By.xpath(HERO_TABLE_TD_XPATH_PRE + "[" + i + "]" + "/td[" + 4 + "]")).getText());
            workingClassHero.setDeathDate(webDriver.findElement(By.xpath(HERO_TABLE_TD_XPATH_PRE + "[" + i + "]" + "/td[" + 5 + "]")).getText());
            workingClassHero.setSalary(webDriver.findElement(By.xpath(HERO_TABLE_TD_XPATH_PRE + "[" + i + "]" + "/td[" + 6 + "]")).getText());
            workingClassHero.setTaxPaid(webDriver.findElement(By.xpath(HERO_TABLE_TD_XPATH_PRE + "[" + i + "]" + "/td[" + 7 + "]")).getText());
            workingClassHero.setBrowniePoints(webDriver.findElement(By.xpath(HERO_TABLE_TD_XPATH_PRE + "[" + i + "]" + "/td[" + 8 + "]")).getText());
            if (workingClassHero.getDeathDate().equalsIgnoreCase("")) {
                workingClassHero.setDeathDate(null);
            }
            if (workingClassHero.getBrowniePoints().equalsIgnoreCase("")) {
                workingClassHero.setBrowniePoints(null);
            }
            heroList.add(workingClassHero);
        }
        return heroList;
    }

    public WebElement getSearchInput() {
        return webDriver.findElement(By.cssSelector(SEARCH_INPUT_LOC_CSS));
    }

}
