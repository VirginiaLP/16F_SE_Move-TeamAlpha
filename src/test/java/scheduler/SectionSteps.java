package scheduler;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



/**
 * Created by kevin on 10/29/16.
 */
public class SectionSteps {
    // givens
    @Given("^there is a section with the (.*?) (\\d+)$")
    public void thereIsASectionWithTheInteger(String attribute, int crn) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^there is a section with the (.*?) \"([^\"]*)\"$")
    public void thereIsASectionWithTheString(String attribute, String value) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^there is a section with the term (.*?)$")
    public void thereIsASectionWithTheTerm(String term) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^there is a section with the term-length (.*?)$")
    public void thereIsASectionWithTheTermLength(String length) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    // whens
    @When("^I ask for the section's (.*?)")
    public void iAskForTheSectionSNumber(String attribute) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    // thens
    @Then("^I receive the integer (\\d+) from the section$")
    public void iReceiveTheIntegerFromTheSection(int expected) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive the string \"([^\"]*)\" from the section$")
    public void iReceiveTheStringFromTheSection(String expected) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive the term-length (.*?) from the section$")
    public void iReceiveTheTermLengthACU_WORLDWIDE_SESSION_FromTheSection(String length) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive the term (.*?) from the section$")
    public void iReceiveTheTermSummerFromTheSection(String term) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
