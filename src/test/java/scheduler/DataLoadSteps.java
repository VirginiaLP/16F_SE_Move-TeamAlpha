package scheduler;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Objects;


public class DataLoadSteps
{
    static String fileName = null;
    static Object resultEntity = null;
    static Object result = null;

    // Givens
    @Given("^the file \"([^\"]*)\" to load$")
    public void theFileToLoad(String file) throws Throwable
    {
        fileName = file;
    }

    // Whens
    @When("^I load the file into the data loader$")
    public void iLoadTheFileIntoTheDataLoader() throws Throwable
    {
        DataLoad.loadGeneral(fileName);
    }

    @When("^I request the student with the banner ID (\\d+)$")
    public void iRequestTheStudentWithTheBannerID(int bannerID) throws Throwable
    {
        resultEntity = DataLoad.students.get(bannerID);
    }

    @When("^I request the professor with the professor ID (\\d+)$")
    public void iRequestTheProfessorWithTheProfessorID(int professorID) throws Throwable
    {
        resultEntity = DataLoad.professors.get(professorID);
    }

    @When("^I request the section with the CRN (\\d+)$")
    public void iRequestTheSectionWithTheCRN(int crn) throws Throwable
    {
        resultEntity = DataLoad.sections.get(crn);
    }

    @When("^I ask for the student's first name from the data loader$")
    public void iAskForTheStudentsFirstNameFromTheDataLoader() throws Throwable
    {
        result = ((Student)resultEntity).getFirstName();
    }

    @When("^I ask for the professor's first name from the data loader$")
    public void iAskForTheProfessorsFirstNameFromTheDataLoader() throws Throwable
    {
        result = ((Professor)resultEntity).getFirstName();
    }

    @When("^I ask for the section's subject from the data loader$")
    public void iAskForTheSectionsSubjectFromTheDataLoader() throws Throwable
    {
        result = ((Section)resultEntity).getSubject();
    }

    // Thens
    @Then("^I receive the string (.*?) from the data loader$")
    public void iReceiveTheString(String expected) throws Throwable
    {
        assertEquals(expected, (String)result);
    }
}