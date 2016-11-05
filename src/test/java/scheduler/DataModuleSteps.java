package scheduler;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import org.apache.commons.io.IOUtils;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

class DataModule
{
    private static final String CONNECTION_URL =
            "jdbc:sqlite:data/schedule_data.db";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static void load(String csvPath) throws SQLException
    {
        try {
            conn = DriverManager.getConnection(CONNECTION_URL);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            /*
            Scanner scan = new Scanner(new File(csvPath));

            while (scan.hasNextLine()) {

            }
            */
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            if (rs != null)
                rs.close();

            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
    }

    public void insertStudent(Student student)
    {

    }

    public void insertProfessor(Professor prof)
    {

    }

    public void insertSection(Section section)
    {

    }

    public Student makeStudent(String firstName, String lastName, String classification)
    {
        if (firstName == null || lastName == null || classification == null)
            return null;

        return new Student(firstName, lastName, classification);
    }

    public Professor makeProfessor(String firstName, String lastName)
    {
        if (firstName == null || lastName == null)
            return null;

        return new Professor(firstName, lastName);
    }

    public Section makeSection(Integer crn, String cTerm, String cTermLength, String sub,
                            String num, String tit, String suf, Integer year, Integer start, Integer end)
    {
        Term sectionTerm = null;
        TermLength sectionTermLength = null;

        for (Term term : Term.values())
        {
            if (cTerm.equals(term.name()))
                sectionTerm = term;
        }

        for (TermLength termLength : TermLength.values())
        {
            if (cTermLength.equals(termLength.name()))
                sectionTermLength = termLength;
        }

        if (crn == null
                || sectionTerm == null
                || sectionTermLength == null
                || sub == null
                || num == null
                || tit == null
                || suf == null
                || year == null
                || start == null
                || end == null)
            return null;

        return new Section(crn, sectionTerm, sectionTermLength, sub, num, tit, suf, year, start, end);
    }
}

public class DataModuleSteps
{
    static String fileName;

    @Given("^the file \"([^\"]*)\" to load$")
    public void theFileToLoad(String file) throws Throwable
    {
        fileName = file;
//        DataModule.load();
    }

    @When("^I load the file into the data module$")
    public void iLoadTheFileIntoTheDataModule() throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I request the student with the banner ID (\\d+)$")
    public void iRequestTheStudentWithTheBannerID(int arg1) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I request the professor with the professor ID (\\d+)$")
    public void iRequestTheProfessorWithTheProfessorID(int professor) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I request the section with the CRN (\\d+)$")
    public void iRequestTheSectionWithTheCRN(int crn) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive the string (.*?) from the data module$")
    public void iReceiveTheString(String expected) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}