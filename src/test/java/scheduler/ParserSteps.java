package scheduler;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import java.io.File;
import java.util.Scanner;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class ParserSteps
{
    static String row;
    static String fileString;
    static String[] tokens;

    @Given("^the file \"([^\"]*)\" to parse$")
    public void theFileToParse(String fileName) throws Throwable {

        ClassLoader classLoader = getClass().getClassLoader();
        fileString = IOUtils.toString(classLoader.getResourceAsStream(fileName));
    }

    @When("^I request line (\\d+) of the file$")
    public void iRequestLineOfTheFile(int line) throws Throwable {
        Scanner scan = new Scanner(fileString);

        for (int count = 1; count <= line; ++count)
            row = scan.nextLine();
    }

    @Then("^I receive the string values:$")
    public void iReceiveTheStringValues(DataTable values) throws Throwable {
        tokens = Parser.parseRow(row);
        List<String> column = values.raw().get(0);
        String[] correctTokens = new String[values.raw().size()];
        for (int index = 0; index < correctTokens.length; ++index) {
            correctTokens[index] = values.raw().get(index).get(0);
        }
        // System.out.println("PRINT HERE: " + column.get(0));
        assertArrayEquals(correctTokens, tokens);
    }
}