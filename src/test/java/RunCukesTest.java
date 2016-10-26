/**
 * Allow Maven to run Cucumber Tests on 10/25/16.
 */
import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", snippets = SnippetType.CAMELCASE)
public class RunCukesTest {
}