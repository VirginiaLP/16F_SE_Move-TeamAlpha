package scheduler;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.ca.I;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.HashSet;
import static org.junit.Assert.assertEquals;

/**
 * Created by kevin on 10/27/16.
 */
public class ProfessorSteps
{
    static Professor prof = null;
    static Object result = null;

    // Names
    @Given("^there is a professor with the (.*?) name \"([^\"]*)\"$")
    public void thereIsAProfessorWithTheName(String whichName, String name) throws Throwable
    {
        prof = new Professor("", "");

        if (whichName.equals("first"))
            prof.setFirstName(name);

        if (whichName.equals("last"))
            prof.setLastName(name);
    }

    @When("^I ask for the professor's (.*?) name$")
    public void iAskForTheProfessorsName(String whichName) throws Throwable
    {
        if (whichName.equals("first"))
            result = prof.getFirstName();

        if (whichName.equals("last"))
            result = prof.getLastName();
    }

    @Then("^I receive the string \"([^\"]*)\" from the professor$")
    public void iReceiveTheStringFromTheProfessor(String expected) throws Throwable
    {
        assertEquals(expected, (String)result);
    }

    // Sections
    @Given("^there is a professor with the following sections:$")
    public void thereIsAProfessorWithTheFollowingSections(DataTable sections) throws Throwable
    {
        prof = new Professor("", "");

        // for each crn number in the list
        for (String crn : sections.raw().get(0))
        {
            // add the number to the professor's sections
            prof.addSection(Integer.parseInt(crn));
        }
    }

    @When("^I ask for the professor's sections$")
    public void iAskForTheProfessorsSections() throws Throwable
    {
        result = prof.getSections();
    }

    @Then("^I receive this set from the professor:$")
    public void iReceiveThisSetFromTheProfessor(DataTable sections) throws Throwable
    {
        // create a set to compare
        HashSet<Integer> expected = new HashSet<Integer>();

        for (String crn : sections.raw().get(0))
            expected.add(Integer.parseInt(crn));

        // compare the sets
        assertEquals(expected, (HashSet<Integer>)result);
    }
}
