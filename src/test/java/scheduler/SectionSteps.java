package scheduler;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

class Section
{
    private int crn;
    private Term term;
    private TermLength termLength;
    private String subject;
    private String number;
    private String title;
    private String suffix;
    private int year;
    private int timeStart;
    private int timeEnd;


    public Section(int regNum, Term cTerm, TermLength length, String sub, String num, String tit, String suf, int year, int start, int end)
    {
        setSection(regNum, cTerm, length, sub, num, tit, suf, year, start, end);
    }

    public void setSection(int regNum, Term cTerm, TermLength length, String sub, String num, String tit, String suf, int year, int start, int end)
    {
        setCRN(regNum);
        setTerm(cTerm);
        setTermLength(length);
        setSubject(sub);
        setNumber(num);
        setTitle(tit);
        setSuffix(suf);
        setYear(year);
        setTimeStart(start);
        setTimeEnd(end);
    }

    public void setCRN(int regNum)
    {
        crn = regNum;
    }

    public void setTerm(Term cTerm)
    {
        term = cTerm;
    }

    public void setTermLength(TermLength length)
    {
        termLength = length;
    }

    public void setSubject(String sub)
    {
        subject = sub;
    }

    public void setNumber(String num)
    {
        number = num;
    }

    public void setTitle(String tit)
    {
        title = tit;
    }

    public void setSuffix(String suf)
    {
        suffix = suf;
    }

    public void setYear(int yr)
    {
        year = yr;
    }

    public void setTimeStart(int start)
    {
        timeStart = start;
    }

    public void setTimeEnd(int end)
    {
        timeEnd = end;
    }

    public int getCRN()
    {
        return crn;
    }

    public Term getTerm()
    {
        return term;
    }

    public TermLength getTermLength()
    {
        return termLength;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getNumber()
    {
        return number;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public int getYear()
    {
        return year;
    }

    public int getTimeStart()
    {
        return timeStart;
    }

    public int getTimeEnd()
    {
        return timeEnd;
    }
}

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
        section = new Section(0, Term.UNDEFINED, TermLength.UNDEFINED, "", "", "", "", 0, 0, 0);

        switch (attribute)
        {
            case "CRN": section.setCRN(value);              break;
            case "year": section.setYear(value);            break;
            case "start time": section.setTimeStart(value); break;
            case "end time": section.setTimeEnd(value);     break;
        }
    }

    @Given("^there is a section with the (.*?) \"([^\"]*)\"$")
    public void thereIsASectionWithTheString(String attribute, String value) throws Throwable
    {
        section = new Section(0, Term.UNDEFINED, TermLength.UNDEFINED, "", "", "", "", 0, 0, 0);

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
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^there is a section with the term-length (.*?)$")
    public void thereIsASectionWithTheTermLength(String length) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    // whens
    @When("^I ask for the section's (.*?)")
    public void iAskForTheSectionsNumber(String attribute) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    // thens
    @Then("^I receive the integer (\\d+) from the section$")
    public void iReceiveTheIntegerFromTheSection(int expected) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive the string \"([^\"]*)\" from the section$")
    public void iReceiveTheStringFromTheSection(String expected) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive the term-length (.*?) from the section$")
    public void iReceiveTheTermLengthFromTheSection(String length) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive the term (.*?) from the section$")
    public void iReceiveTheTermFromTheSection(String term) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
