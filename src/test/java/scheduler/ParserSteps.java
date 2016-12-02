package scheduler;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.List;

public class ParserSteps
{
    static String row;
    static Path file;
    static String[] tokens;

    @Given("^the file \"([^\"]*)\" to parse$")
    public void theFileToParse(String fileName) throws Throwable
    {
        ClassLoader classLoader = getClass().getClassLoader();
        file = Paths.get(classLoader.getResource(fileName).toURI());
    }

    @When("^I request line (\\d+) of the file$")
    public void iRequestLineOfTheFile(int line) throws Throwable
    {
        Scanner scan = new Scanner(file);

        // identify the desired row
        for (int count = 1; count <= line; ++count)
            row = scan.nextLine();
    }

    @Then("^I receive the string values:$")
    public void iReceiveTheStringValues(DataTable values) throws Throwable
    {
        // parse the row
        tokens = Parser.parseRow(row);

        // get the correct values
        List<String> record = values.raw().get(0);
        String[] correctTokens = record.toArray(new String[record.size()]);

        // compare the parsed row to the correct values
        assertArrayEquals(correctTokens, tokens);
    }
}