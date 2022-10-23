package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import module.HeroTable;
import module.WorkingClassHero;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

public class TestUtils {

    public static String getRandomHeroName(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomGender() {
        Random random = new Random();
        int s = random.nextInt(10);
        if (s > 5) {
            return "MALE";
        } else {
            return "FEMALE";
        }
    }

    public static void updateCSV(String fileToUpdate, String replace, int row, int col) {
        File inputFile = new File(fileToUpdate);
        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile));
            List<String[]> csvBody = null;
            try {
                csvBody = reader.readAll();
            } catch (CsvException e) {
                e.printStackTrace();
            }
            csvBody.get(row)[col] = replace;
            reader.close();

            CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void generateHeroCreationFile(String fileToUpdate, List<WorkingClassHero> heroes) {
        File inputFile = new File(fileToUpdate);
        try {
            List<String[]> newCsvBody = new ArrayList<>();
            for (int i = 0; i < heroes.size(); i++) {
                //natid,name,gender,birthDate,deathDate,salary,taxPaid,browniePoints
                String[] newHero = new String[8];
                newHero[0] = heroes.get(i).getNatid();
                newHero[1] = heroes.get(i).getName();
                newHero[2] = heroes.get(i).getGender();
                newHero[3] = heroes.get(i).getBirthDate();
                newHero[4] = heroes.get(i).getDeathDate();
                newHero[5] = heroes.get(i).getSalary();
                newHero[6] = heroes.get(i).getTaxPaid();
                newHero[7] = heroes.get(i).getBrowniePoints();
                newCsvBody.add(newHero);
            }
            CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
            writer.writeAll(newCsvBody);
            writer.flush();
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void addHeroIntoCsv(String fileToUpdate, WorkingClassHero hero) {
        File inputFile = new File(fileToUpdate);
        try {
            CSVReader csvReader = new CSVReader(new FileReader(inputFile));
            List<String[]> csvBody = csvReader.readAll();
            //natid,name,gender,birthDate,deathDate,salary,taxPaid,browniePoints
            String[] newHero = new String[8];
            newHero[0] = hero.getNatid();
            newHero[1] = hero.getName();
            newHero[2] = hero.getGender();
            newHero[3] = hero.getBirthDate();
            newHero[4] = hero.getDeathDate();
            newHero[5] = hero.getSalary();
            newHero[6] = hero.getTaxPaid();
            newHero[7] = hero.getBrowniePoints();
            csvBody.add(newHero);
            CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

    }

    public static WorkingClassHero buildDefaultHero() throws SQLException {
        ResultSet resultSet = DBUtils.getLastHero();
        String lastNatid = "";
        while (resultSet.next()) {
            lastNatid = resultSet.getString(HeroTable.NATID);
        }
        String[] natidArr = lastNatid.split("-");
        String newNatid = "natid-" + (Integer.parseInt(natidArr[1]) + 1);
        String name = TestUtils.getRandomHeroName(5) + " " + TestUtils.getRandomHeroName(5);
        String gender = TestUtils.getRandomGender();
        String birthDate = "1930-01-01T09:00:00";
        String deathDate = null; // "1930-01-01T09:00:00" triggered an issue, raised in the issue list.
        String browniePoints = "9";
        String salary = "10000";
        String taxPaid = "1000";
        WorkingClassHero workingClassHero = new WorkingClassHero(newNatid, name, gender, birthDate, deathDate,
                browniePoints, salary, taxPaid);
        return workingClassHero;
    }

    public static List<WorkingClassHero> buildHeroTestList(int size) throws SQLException {
        List<WorkingClassHero> heroes = new ArrayList<>();
        WorkingClassHero firstHero = buildDefaultHero();
        int firstId = Integer.parseInt(firstHero.getNatid().substring(6));
        for (int i = 1; i <= size; i++) {
            if (i > 1) {
                WorkingClassHero hero = buildDefaultHero();
                hero.setNatid("natid-" + (firstId + i - 1));
                heroes.add(hero);
            } else {
                heroes.add(firstHero);
            }
        }
        return heroes;
    }

}
