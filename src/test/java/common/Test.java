package common;

import module.WorkingClassHero;
import utils.TestUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws SQLException {
        WorkingClassHero workingClassHero = TestUtils.buildDefaultHero();
        List<WorkingClassHero> workingClassHeroes = TestUtils.buildHeroTestList(10);
        TestUtils.generateHeroCreationFile("src/test/resources/testdata/hero_creation.csv", workingClassHeroes);
    }

}
