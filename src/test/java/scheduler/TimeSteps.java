package scheduler;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;


/**
 * Created by kevin on 11/12/16.
 */
public class TimeSteps
{
    Time time;
    Object result;

    // givens
    @Given("^there is a time with the (.*?) (\\d+)$")
    public void thereIsATimeWithTheInteger(String attribute, int value) throws Throwable
    {
        time = new Time(0, 0, 0, Term.UNDEFINED, TermLength.UNDEFINED);

        switch (attribute)
        {
            case "start-time": time.setTimeStart(value);     break;
            case "end-time": time.setTimeEnd(value);         break;
            case "year": time.setYear(value);                break;
        }
    }

    @Given("^there is a time with the term (.*?)$")
    public void thereIsATimeWithTheTerm(String term) throws Throwable
    {
        time = new Time(0, 0, 0, Term.UNDEFINED, TermLength.UNDEFINED);

        switch (term.toUpperCase())
        {
            case "FALL": time.setTerm(Term.FALL);        break;
            case "SPRING": time.setTerm(Term.SPRING);    break;
            case "SUMMER": time.setTerm(Term.SUMMER);    break;
        }
    }

    @Given("^there is a time with the term-length (.*?)$")
    public void thereIsATimeWithTheTermLength(String length) throws Throwable
    {
        time = new Time(0, 0, 0, Term.UNDEFINED, TermLength.UNDEFINED);

        switch (length.toUpperCase())
        {
            case "ACU_WORLDWIDE_SESSION_1": time.setTermLength(TermLength.ACU_WORLDWIDE_SESSION_1);           break;
            case "ACU_WORLDWIDE_SESSION_2": time.setTermLength(TermLength.ACU_WORLDWIDE_SESSION_2);           break;
            case "DMIN_INTENSIVE_COURSE": time.setTermLength(TermLength.DMIN_INTENSIVE_COURSE);               break;
            case "FULL_TERM": time.setTermLength(TermLength.FULL_TERM);                                       break;
            case "INTENSIVE_COURSE": time.setTermLength(TermLength.INTENSIVE_COURSE);                         break;
            case "JANUARY_INTENSIVE_COURSE": time.setTermLength(TermLength.JANUARY_INTENSIVE_COURSE);         break;
            case "SESSION_I": time.setTermLength(TermLength.SESSION_I);                                       break;
            case "SESSION_I_EXTENDED_COURSE": time.setTermLength(TermLength.SESSION_I_EXTENDED_COURSE);       break;
            case "SESSION_II": time.setTermLength(TermLength.SESSION_II);                                     break;
            case "SESSION_II_EXTENDED_COURSE": time.setTermLength(TermLength.SESSION_II_EXTENDED_COURSE);     break;
            case "SESSION_III": time.setTermLength(TermLength.SESSION_III);                                   break;
            case "SESSION_III_EXTENDED_COURSE": time.setTermLength(TermLength.SESSION_III_EXTENDED_COURSE);   break;
            case "SESSION_IV": time.setTermLength(TermLength.SESSION_IV);                                     break;
            case "SESSION_IV_EXTENDED_COURSE": time.setTermLength(TermLength.SESSION_IV_EXTENDED_COURSE);     break;
        }
    }

    // whens
    @When("^I ask for the time's ([^\"\\s]*)$")
    public void iAskForTheTimes(String attribute) throws Throwable
    {
        switch (attribute)
        {
            case "start-time": result = time.getTimeStart();     break;
            case "end-time": result = time.getTimeEnd();         break;
            case "year": result = time.getYear();                break;
            case "term": result = time.getTerm();                break;
            case "term-length": result = time.getTermLength();   break;
        }
    }

    // thens
    @Then("^I receive the integer (\\d+) from the time$")
    public void iReceiveTheIntegerFromTheSection(Integer expected) throws Throwable
    {
        assertEquals(expected, result);
    }

    @Then("^I receive the string \"([^\"]*)\" from the time$")
    public void iReceiveTheStringFromTheSection(String expected) throws Throwable
    {
        assertEquals(expected, result);
    }

    @Then("^I receive the term (.*?) from the time$")
    public void iReceiveTheTermFromTheSection(String expected) throws Throwable
    {
        assertEquals(expected.toUpperCase(), ((Term)(result)).name());
    }

    @Then("^I receive the term-length (.*?) from the time$")
    public void iReceiveTheTermLengthFromTheSection(String expected) throws Throwable
    {
        assertEquals(expected.toUpperCase(), ((TermLength)(result)).name());
    }
}
