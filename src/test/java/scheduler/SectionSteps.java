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
        section = new Section(Term.UNDEFINED, TermLength.UNDEFINED, "", "", "", "", 0, 0, 0, 0);
        switch (attribute)
        {
            case "year": section.setYear(value);                break;
            case "start time": section.setTimeStart(value);     break;
            case "end time": section.setTimeEnd(value);         break;
            case "building ID": section.setBuildingID(value);   break;
        }
    }

    @Given("^there is a section with the (.*?) \"([^\"]*)\"$")
    public void thereIsASectionWithTheString(String attribute, String value) throws Throwable
    {
        section = new Section(Term.UNDEFINED, TermLength.UNDEFINED, "", "", "", "", 0, 0, 0, 0);
        switch (attribute)
        {
            case "subject": section.setSubject(value);      break;
            case "number": section.setNumber(value);        break;
            case "title": section.setTitle(value);          break;
            case "suffix": section.setSuffix(value);        break;
        }
    }

    @Given("^there is a section with the term (.*?)$")
    public void thereIsASectionWithTheTerm(String term) throws Throwable
    {
        section = new Section(Term.UNDEFINED, TermLength.UNDEFINED, "", "", "", "", 0, 0, 0, 0);
        switch (term.toUpperCase())
        {
            case "FALL": section.setTerm(Term.FALL);        break;
            case "SPRING": section.setTerm(Term.SPRING);    break;
            case "SUMMER": section.setTerm(Term.SUMMER);    break;
        }
    }

    @Given("^there is a section with the term-length (.*?)$")
    public void thereIsASectionWithTheTermLength(String length) throws Throwable
    {
        section = new Section(Term.UNDEFINED, TermLength.UNDEFINED, "", "", "", "", 0, 0, 0, 0);
        switch (length.toUpperCase())
        {
            case "ACU_WORLDWIDE_SESSION_1": section.setTermLength(TermLength.ACU_WORLDWIDE_SESSION_1);           break;
            case "ACU_WORLDWIDE_SESSION_2": section.setTermLength(TermLength.ACU_WORLDWIDE_SESSION_2);           break;
            case "DMIN_INTENSIVE_COURSE": section.setTermLength(TermLength.DMIN_INTENSIVE_COURSE);               break;
            case "FULL_TERM": section.setTermLength(TermLength.FULL_TERM);                                       break;
            case "INTENSIVE_COURSE": section.setTermLength(TermLength.INTENSIVE_COURSE);                         break;
            case "JANUARY_INTENSIVE_COURSE": section.setTermLength(TermLength.JANUARY_INTENSIVE_COURSE);         break;
            case "SESSION_I": section.setTermLength(TermLength.SESSION_I);                                       break;
            case "SESSION_I_EXTENDED_COURSE": section.setTermLength(TermLength.SESSION_I_EXTENDED_COURSE);       break;
            case "SESSION_II": section.setTermLength(TermLength.SESSION_II);                                     break;
            case "SESSION_II_EXTENDED_COURSE": section.setTermLength(TermLength.SESSION_II_EXTENDED_COURSE);     break;
            case "SESSION_III": section.setTermLength(TermLength.SESSION_III);                                   break;
            case "SESSION_III_EXTENDED_COURSE": section.setTermLength(TermLength.SESSION_III_EXTENDED_COURSE);   break;
            case "SESSION_IV": section.setTermLength(TermLength.SESSION_IV);                                     break;
            case "SESSION_IV_EXTENDED_COURSE": section.setTermLength(TermLength.SESSION_IV_EXTENDED_COURSE);     break;
        }
    }

    // whens
    @When("^I ask for the section's (.*?)$")
    public void iAskForTheSections(String attribute) throws Throwable
    {
        switch (attribute)
        {
            case "year": result = section.getYear();                break;
            case "start time": result = section.getTimeStart();     break;
            case "end time": result = section.getTimeEnd();         break;
            case "subject": result = section.getSubject();          break;
            case "number": result = section.getNumber();            break;
            case "title": result = section.getTitle();              break;
            case "suffix": result = section.getSuffix();            break;
            case "term": result = section.getTerm();                break;
            case "term-length": result = section.getTermLength();   break;
            case "building ID": result = section.getBuildingID();   break;
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

    @Then("^I receive the term (.*?) from the section$")
    public void iReceiveTheTermFromTheSection(String expected) throws Throwable
    {
        assertEquals(expected.toUpperCase(), ((Term)(result)).name());
    }

    @Then("^I receive the term-length (.*?) from the section$")
    public void iReceiveTheTermLengthFromTheSection(String expected) throws Throwable
    {
        assertEquals(expected.toUpperCase(), ((TermLength)(result)).name());
    }
}
