package stepdefs;

import common.Constants;
import common.Url;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import module.HeroTable;
import module.WorkingClassHero;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.ClerkDashboardPage;
import pages.GovernorDashboardPage;
import pages.LoginPage;
import utils.DBUtils;
import utils.TestUtils;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestStepDef {

    private WebDriver webDriver;
    private List<WorkingClassHero> testUploadHeroes = new ArrayList<>();

    private LoginPage loginPage;
    private ClerkDashboardPage clerkDashboardPage;
    private GovernorDashboardPage governorDashboardPage;

    private File csvFile;

    @Given("Launch the browser and login clerk account")
    public void launchBrowserAndLoginClerkAccount() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get(Url.LOGIN_URL);
        new LoginPage(webDriver).login(Constants.CLERK_USER, Constants.PASSWORD);
        clerkDashboardPage = new ClerkDashboardPage(webDriver);
        Assert.assertEquals(clerkDashboardPage.getTitleElement().getText(),
                "Clerk Dashboard", "Clerk Dashboard doesn't show up.");
        webDriver.navigate().to(Url.CLERK_UPLOAD_CSV_URL);
        Assert.assertEquals(clerkDashboardPage.getCsvUploadTitleElement().getText(),
                "Upload CSV file", "Upload CSV file page doesn't show up.");
    }

    @Given("Csv file with correct fields")
    public void csvFileWithCorrectFields() throws SQLException {
        testUploadHeroes = TestUtils.buildHeroTestList(10);
        TestUtils.generateHeroCreationFile("src/test/resources/testdata/hero_creation.csv", testUploadHeroes);
        csvFile = new File("src/test/resources/testdata/hero_creation.csv");
    }

    @Then("Upload via the csv uploading page and verify the result")
    public void uploadViaTheCsvUploadingPageAndVerifyTheResult() throws InterruptedException {
        clerkDashboardPage.getCsvUploadInputElement().sendKeys(csvFile.getAbsolutePath());
        Thread.sleep(1000);
        clerkDashboardPage.getCsvUploadCreateBtn().click();
        Assert.assertEquals(clerkDashboardPage.getCsvUploadResultTextElement().getText(),
                "Created Successfully!", "Created Successfully! message doesn't show up.");
    }

    @And("Verify the new created correct hero from the working class heroes table record")
    public void verifyTheNewCreatedCorrectHeroFromTheWorkingClassHeroesTableRecord() throws SQLException {
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < testUploadHeroes.size(); i++) {
            String natid = testUploadHeroes.get(i).getNatid();
            ResultSet resultSet = DBUtils.getHero(natid);

            while (resultSet.next()) {
                softAssert.assertEquals(resultSet.getString(HeroTable.NATID),
                        natid, "natid is wrong. for natid: " + natid);
                softAssert.assertEquals(resultSet.getString(HeroTable.NAME),
                        testUploadHeroes.get(i).getName(), "name is wrong. for natid: " + natid);
                softAssert.assertEquals(resultSet.getString(HeroTable.GENDER),
                        testUploadHeroes.get(i).getGender(), "gender is wrong. for natid: " + natid);
                softAssert.assertEquals(resultSet.getString(HeroTable.BIRTH_DATE).substring(0, 19).replace(" ", "T"),
                        testUploadHeroes.get(i).getBirthDate(), "birth date is wrong. for natid: " + natid);
                softAssert.assertEquals(resultSet.getString(HeroTable.DEATH_DATE),
                        testUploadHeroes.get(i).getDeathDate(), "death date is wrong. for natid: " + natid);
                softAssert.assertEquals(resultSet.getString(HeroTable.BROWNIE_POINTS),
                        testUploadHeroes.get(i).getBrowniePoints(), "browniePoints is wrong. for natid: " + natid);
                softAssert.assertEquals(resultSet.getString(HeroTable.SALARY),
                        testUploadHeroes.get(i).getSalary(), "salary is wrong. for natid: " + natid);
                softAssert.assertEquals(resultSet.getString(HeroTable.TAX_PAID),
                        testUploadHeroes.get(i).getTaxPaid(), "tax paid is wrong. for natid: " + natid);
            }
        }
        softAssert.assertAll();
    }

    @And("Close the browser")
    public void closeTheBrowser() {
        webDriver.close();
        webDriver.quit();
    }

    @Given("Csv file with one row contains incorrect data")
    public void csvFileWithOneRowContainsIncorrectData() throws SQLException {
        testUploadHeroes = TestUtils.buildHeroTestList(1);
        TestUtils.generateHeroCreationFile("src/test/resources/testdata/hero_creation_partial_error.csv", testUploadHeroes);
        WorkingClassHero brokenHeroTestData = TestUtils.buildDefaultHero();
        brokenHeroTestData.setNatid("natid-1");
        TestUtils.addHeroIntoCsv("src/test/resources/testdata/hero_creation_partial_error.csv", brokenHeroTestData);
        csvFile = new File("src/test/resources/testdata/hero_creation_partial_error.csv");
    }


    @Then("Upload via the csv uploading page and verify the unsuccessful result")
    public void uploadViaTheCsvUploadingPageAndVerifyTheUnsuccessfulResult() throws InterruptedException {
        clerkDashboardPage.getCsvUploadInputElement().sendKeys(csvFile.getAbsolutePath());
        Thread.sleep(1000);
        clerkDashboardPage.getCsvUploadCreateBtn().click();
        Assert.assertEquals(clerkDashboardPage.getCsvUploadResultTextElement().getText(),
                "Unable to create hero!", "Created Successfully! message doesn't show up.");
    }

    @Given("Launch the browser and login gov account")
    public void launchTheBrowserAndLoginGovAccount() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get(Url.LOGIN_URL);
        new LoginPage(webDriver).login(Constants.GOV_USER, Constants.PASSWORD);
        governorDashboardPage = new GovernorDashboardPage(webDriver);
    }

    @Given("Gov user open the Governor Dashboard page")
    public void govUserOpenTheGovernorDashboardPage() {
        Assert.assertEquals(governorDashboardPage.getTitleElement().getText(),
                "Governor Dashboard", "Governor Dashboard\n doesn't show up.");
    }


    @Then("Click on the List All button")
    public void clickOnTheListAllButton() {
        governorDashboardPage.getListAllButton().click();
    }

    @And("Verify the List data matches the database")
    public void verifyTheListDataMatchesTheDatabase() throws SQLException {
        List<WorkingClassHero> heroList = governorDashboardPage.getHeroSearchAllTableContent();
        ResultSet resultSet = DBUtils.query("select * from working_class_heroes limit " + heroList.size());
        List<WorkingClassHero> expectedHeroList = new ArrayList<>();
        while (resultSet.next()) {
            WorkingClassHero hero = new WorkingClassHero();
            hero.setNatid(resultSet.getString(HeroTable.NATID));
            hero.setName(resultSet.getString(HeroTable.NAME));
            hero.setGender(resultSet.getString(HeroTable.GENDER));
            hero.setBirthDate(resultSet.getString(HeroTable.BIRTH_DATE));
            hero.setDeathDate(resultSet.getString(HeroTable.DEATH_DATE));
            hero.setSalary(resultSet.getString(HeroTable.SALARY));
            hero.setTaxPaid(resultSet.getString(HeroTable.TAX_PAID));
            hero.setBrowniePoints(resultSet.getString(HeroTable.BROWNIE_POINTS));
            // format the taxPaid
            DecimalFormat df = new DecimalFormat("#.##");
            hero.setTaxPaid(df.format(Float.parseFloat(hero.getTaxPaid())));
            expectedHeroList.add(hero);
        }
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < heroList.size(); i++) {
            String natid = heroList.get(i).getNatid();
            softAssert.assertEquals(heroList.get(i).getNatid(),
                    expectedHeroList.get(i).getNatid(), "natid is wrong. for natid: " + natid);
            softAssert.assertEquals(heroList.get(i).getName(),
                    expectedHeroList.get(i).getName(), "name is wrong. for natid: " + natid);
            softAssert.assertEquals(heroList.get(i).getGender(),
                    expectedHeroList.get(i).getGender(), "gender is wrong. for natid: " + natid);
            softAssert.assertEquals(heroList.get(i).getBirthDate(),
                    expectedHeroList.get(i).getBirthDate(), "birth date is wrong. for natid: " + natid);
            softAssert.assertEquals(heroList.get(i).getDeathDate(),
                    expectedHeroList.get(i).getDeathDate(), "death date is wrong. for natid: " + natid);
            softAssert.assertEquals(heroList.get(i).getBrowniePoints(),
                    expectedHeroList.get(i).getBrowniePoints(), "browniePoints is wrong. for natid: " + natid);
            float actualSalary = Float.parseFloat(heroList.get(i).getSalary());
            float expectedSalary = Float.parseFloat(expectedHeroList.get(i).getSalary());
            softAssert.assertTrue((actualSalary - expectedSalary) == 0, "salary is wrong. for natid: " + natid);
            float actualTaxPaid = Float.parseFloat(heroList.get(i).getTaxPaid());
            float expectedTaxPaid = Float.parseFloat(expectedHeroList.get(i).getTaxPaid());
            softAssert.assertTrue((actualTaxPaid - expectedTaxPaid) == 0, "tax paid is wrong. for natid: " + natid);
        }
        softAssert.assertAll();
    }

    @Then("Search natid")
    public void searchNatid() {
        governorDashboardPage.getSearchInput().sendKeys("natid-1");
    }

    @And("Verify the natid search result matches the database")
    public void verifyTheNatidSearchResultMatchesTheDatabase() throws SQLException {
        List<WorkingClassHero> heroList = governorDashboardPage.getHeroSearchAllTableContent();
        List<WorkingClassHero> actualHeroList = new ArrayList<>();
        actualHeroList.add(heroList.get(0));

        ResultSet resultSet = DBUtils.query("select * from working_class_heroes where natid = 'natid-1'");
        List<WorkingClassHero> expectedHeroList = new ArrayList<>();
        while (resultSet.next()) {
            WorkingClassHero hero = new WorkingClassHero();
            hero.setNatid(resultSet.getString(HeroTable.NATID));
            hero.setName(resultSet.getString(HeroTable.NAME));
            hero.setGender(resultSet.getString(HeroTable.GENDER));
            hero.setBirthDate(resultSet.getString(HeroTable.BIRTH_DATE));
            hero.setDeathDate(resultSet.getString(HeroTable.DEATH_DATE));
            hero.setSalary(resultSet.getString(HeroTable.SALARY));
            hero.setTaxPaid(resultSet.getString(HeroTable.TAX_PAID));
            hero.setBrowniePoints(resultSet.getString(HeroTable.BROWNIE_POINTS));
            // format the taxPaid
            DecimalFormat df = new DecimalFormat("#.##");
            hero.setTaxPaid(df.format(Float.parseFloat(hero.getTaxPaid())));
            expectedHeroList.add(hero);
        }
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < actualHeroList.size(); i++) {
            String natid = actualHeroList.get(i).getNatid();
            softAssert.assertEquals(actualHeroList.get(i).getNatid(),
                    expectedHeroList.get(i).getNatid(), "natid is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getName(),
                    expectedHeroList.get(i).getName(), "name is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getGender(),
                    expectedHeroList.get(i).getGender(), "gender is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getBirthDate(),
                    expectedHeroList.get(i).getBirthDate(), "birth date is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getDeathDate(),
                    expectedHeroList.get(i).getDeathDate(), "death date is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getBrowniePoints(),
                    expectedHeroList.get(i).getBrowniePoints(), "browniePoints is wrong. for natid: " + natid);
            float actualSalary = Float.parseFloat(actualHeroList.get(i).getSalary());
            float expectedSalary = Float.parseFloat(expectedHeroList.get(i).getSalary());
            softAssert.assertTrue((actualSalary - expectedSalary) == 0, "salary is wrong. for natid: " + natid);
            float actualTaxPaid = Float.parseFloat(actualHeroList.get(i).getTaxPaid());
            float expectedTaxPaid = Float.parseFloat(expectedHeroList.get(i).getTaxPaid());
            softAssert.assertTrue((actualTaxPaid - expectedTaxPaid) == 0, "tax paid is wrong. for natid: " + natid);
        }
        softAssert.assertAll();
    }

    @Then("Search name")
    public void searchName() {
        governorDashboardPage.getSearchInput().sendKeys("Danielle Fritsch MD");
    }

    @And("Verify the name search result matches the database")
    public void verifyTheNameSearchResultMatchesTheDatabase() throws SQLException {
        List<WorkingClassHero> heroList = governorDashboardPage.getHeroSearchAllTableContent();
        List<WorkingClassHero> actualHeroList = new ArrayList<>();
        actualHeroList.add(heroList.get(0));

        ResultSet resultSet = DBUtils.query("select * from working_class_heroes where name = 'Danielle Fritsch MD'");
        List<WorkingClassHero> expectedHeroList = new ArrayList<>();
        while (resultSet.next()) {
            WorkingClassHero hero = new WorkingClassHero();
            hero.setNatid(resultSet.getString(HeroTable.NATID));
            hero.setName(resultSet.getString(HeroTable.NAME));
            hero.setGender(resultSet.getString(HeroTable.GENDER));
            hero.setBirthDate(resultSet.getString(HeroTable.BIRTH_DATE));
            hero.setDeathDate(resultSet.getString(HeroTable.DEATH_DATE));
            hero.setSalary(resultSet.getString(HeroTable.SALARY));
            hero.setTaxPaid(resultSet.getString(HeroTable.TAX_PAID));
            hero.setBrowniePoints(resultSet.getString(HeroTable.BROWNIE_POINTS));
            // format the taxPaid
            DecimalFormat df = new DecimalFormat("#.##");
            hero.setTaxPaid(df.format(Float.parseFloat(hero.getTaxPaid())));
            expectedHeroList.add(hero);
        }
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < actualHeroList.size(); i++) {
            String natid = actualHeroList.get(i).getNatid();
            softAssert.assertEquals(actualHeroList.get(i).getNatid(),
                    expectedHeroList.get(i).getNatid(), "natid is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getName(),
                    expectedHeroList.get(i).getName(), "name is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getGender(),
                    expectedHeroList.get(i).getGender(), "gender is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getBirthDate(),
                    expectedHeroList.get(i).getBirthDate(), "birth date is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getDeathDate(),
                    expectedHeroList.get(i).getDeathDate(), "death date is wrong. for natid: " + natid);
            softAssert.assertEquals(actualHeroList.get(i).getBrowniePoints(),
                    expectedHeroList.get(i).getBrowniePoints(), "browniePoints is wrong. for natid: " + natid);
            float actualSalary = Float.parseFloat(actualHeroList.get(i).getSalary());
            float expectedSalary = Float.parseFloat(expectedHeroList.get(i).getSalary());
            softAssert.assertTrue((actualSalary - expectedSalary) == 0, "salary is wrong. for natid: " + natid);
            float actualTaxPaid = Float.parseFloat(actualHeroList.get(i).getTaxPaid());
            float expectedTaxPaid = Float.parseFloat(expectedHeroList.get(i).getTaxPaid());
            softAssert.assertTrue((actualTaxPaid - expectedTaxPaid) == 0, "tax paid is wrong. for natid: " + natid);
        }
        softAssert.assertAll();
    }
}
