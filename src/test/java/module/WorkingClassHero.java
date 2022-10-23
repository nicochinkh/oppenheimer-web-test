package module;

public class WorkingClassHero {

    //natid,name,gender,birthDate,deathDate,salary,taxPaid,browniePoints
    public final static String COL_NAME_NATID = "natid";
    public final static String COL_NAME_NAME = "name";
    public final static String COL_NAME_GENDER = "gender";
    public final static String COL_NAME_BIRTH_DATE = "birthDate";
    public final static String COL_NAME_DEATH_DATE = "deathDate";
    public final static String COL_NAME_SALARY = "salary";
    public final static String COL_NAME_TAX_PAID = "taxPaid";
    public final static String COL_NAME_BROWNIE_POINTS = "browniePoints";

    private String natid;
    private String name;
    private String gender;
    private String birthDate;
    private String deathDate;
    private String browniePoints;
    private String salary;
    private String taxPaid;

    public WorkingClassHero(String natid, String name, String gender, String birthDate,
                            String deathDate, String browniePoints, String salary, String taxPaid) {
        this.natid = natid;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.browniePoints = browniePoints;
        this.salary = salary;
        this.taxPaid = taxPaid;
    }

    public String getNatid() {
        return natid;
    }

    public void setNatid(String natid) {
        this.natid = natid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getBrowniePoints() {
        return browniePoints;
    }

    public void setBrowniePoints(String browniePoints) {
        this.browniePoints = browniePoints;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTaxPaid() {
        return taxPaid;
    }

    public void setTaxPaid(String taxPaid) {
        this.taxPaid = taxPaid;
    }
}
