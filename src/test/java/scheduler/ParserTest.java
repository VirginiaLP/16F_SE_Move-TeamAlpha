import scheduler.Parser;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert.*;
import java.io.File;
import java.util.Scanner;
import java.util.List;

public class ParserTest
{
    static Scanner scan;
    static String row;
    static String[] tokens;

    @Given("^the file \"([^\"]*)\" to parse$")
    public void theFileToParse(String fileName) throws Throwable {
        /*
        System.out.println("THE STRING IS NULL");
        System.out.println(fileName);
        ClassLoader classLoader = getClass().getClassLoader();
        System.out.println("IS CLASS LOADER NULL?");
        System.out.println(classLoader);
        File file = new File(classLoader.getResource(new String("src/test/resources/" + fileName)).getFile());
        if (file == null)
            System.out.println("YUP IT DIDNT FIND ANYTHING");
        else
            System.out.println("I am running");
        */

        // System.out.println("FILENAME IS NOT NULL: " + fileName);
        File file = new File(fileName);
        // System.out.println("FILE IS NOT NULL: " + file);
        scan = new Scanner(file);
        // System.out.println("SCANNER IS NOT NULL: " + scan);
    }

    @When("^I request line (\\d+) of the file$")
    public void iRequestLineOfTheFile(int line) throws Throwable {
        row = "";

        for (int count = 1; count <= line; ++count)
            row = scan.nextLine();
    }

    @Then("^I receive the string values:$")
    public void iReceiveTheStringValues(DataTable values) throws Throwable {
        tokens = Parser.parseRow(row);
        List<String> column = values.raw().get(0);
        String[] correctTokens = column.toArray(new String[column.size()]);
        // assertArrayEquals(correctTokens, tokens);
    }
}