package scheduler;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import java.util.HashSet;

/**
 * Created by kevin on 10/27/16.
 */
public class StudentSteps
{
    Student student = null;
    Object result = null;

    // Names
    @Given("^there is a student with the (.*?) name \"([^\"]*)\"$")
    public void thereIsAStudentWithTheName(String whichString, String name) throws Throwable
    {
        student = new Student("", "", "");

        if (whichString.equals("first"))
            student.setFirstName(name);

        if (whichString.equals("last"))
            student.setLastName(name);
    }

    @When("^I ask for the student's (.*?) name$")
    public void iAskForTheStudentsName(String whichName) throws Throwable
    {
        if (whichName.equals("first"))
            result = student.getFirstName();

        if (whichName.equals("last"))
            result = student.getLastName();
    }

    @Then("^I receive the string \"([^\"]*)\" from the student$")
    public void iReceiveTheStringFromTheStudent(String expected) throws Throwable
    {
        assertEquals(expected, (String)result);
    }

    // Classifications
    @Given("^there is a student with the classification \"([^\"]*)\"$")
    public void thereIsAStudentWithTheClassification(String classification) throws Throwable
    {
        student = new Student("", "", classification);
    }

    @When("^I ask for the student's classification$")
    public void iAskForTheStudentsClassification() throws Throwable
    {
        result = student.getClassification();
    }

    @Then("^I recieve the string \"([^\"]*)\" from the student$")
    public void iRecieveTheStringFromTheStudent(String expected) throws Throwable
    {
        assertEquals(expected, (String)result);
    }

    // Sections
    @Given("^there is a student with the following sections:$")
    public void thereIsAStudentWithTheFollowingSections(DataTable sections) throws Throwable
    {
        student = new Student("", "", "");

        // for each crn number in the list
        for (String crn : sections.raw().get(0))
        {
            // add the number to the professor's sections
            student.addSection(Integer.parseInt(crn));
        }
    }

    @When("^I ask for the student's sections$")
    public void iAskForTheStudentsSections() throws Throwable
    {
        result = student.getSections();
    }

    @Then("^I receive this set from the student:$")
    public void iReceiveThisSetFromTheStudent(DataTable sections) throws Throwable
    {
        // create a set to compare
        HashSet<Integer> expected = new HashSet<Integer>();

        for (String crn : sections.raw().get(0))
            expected.add(Integer.parseInt(crn));

        // compare the sets
        assertEquals(expected, (HashSet<Integer>)result);
    }
}