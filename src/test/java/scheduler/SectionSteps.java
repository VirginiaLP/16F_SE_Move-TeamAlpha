package scheduler;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;


/**
 * Created by kevin on 10/29/16.
 */
public class SectionSteps
{
    static Section section;
    static Object result;

    // givens
    @Given("^there is a section with the (.*?) (\\d+)$")
    public void thereIsASectionWithTheInteger(String attribute, int value) throws Throwable
    {
        section = new Section("", "", "", "", 0, 0);
        switch (attribute)
        {
            case "time-ID": section.setTimeID(value);       break;
            case "room-ID": section.setRoomID(value);       break;
        }
    }

    @Given("^there is a section with the (.*?) \"([^\"]*)\"$")
    public void thereIsASectionWithTheString(String attribute, String value) throws Throwable
    {
        section = new Section("", "", "", "", 0, 0);
        switch (attribute)
        {
            case "subject": section.setSubject(value);      break;
            case "number": section.setNumber(value);        break;
            case "title": section.setTitle(value);          break;
            case "suffix": section.setSuffix(value);        break;
        }
    }

    // whens
    @When("^I ask for the section's ([^\"\\s]*)$")
    public void iAskForTheSections(String attribute) throws Throwable
    {
        switch (attribute)
        {
            case "subject": result = section.getSubject();          break;
            case "number": result = section.getNumber();            break;
            case "title": result = section.getTitle();              break;
            case "suffix": result = section.getSuffix();            break;
            case "time-ID": result = section.getTimeID();          break;
            case "room-ID": result = section.getRoomID();           break;
        }
    }

    // thens
    @Then("^I receive the integer (\\d+) from the section$")
    public void iReceiveTheIntegerFromTheSection(Integer expected) throws Throwable
    {
        assertEquals(expected, result);
    }

    @Then("^I receive the string \"([^\"]*)\" from the section$")
    public void iReceiveTheStringFromTheSection(String expected) throws Throwable
    {
        assertEquals(expected, result);
    }
}
