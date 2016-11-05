package scheduler;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import org.apache.commons.io.IOUtils;

class DataModule
{
    public void load(String path)
    {

    }
}

public class DataModuleSteps
{
    @Given("^the file \"([^\"]*)\" to load$")
    public void theFileToLoad(String fileName) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
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