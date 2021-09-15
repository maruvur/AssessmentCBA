package cucumber.options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class) 
@CucumberOptions(plugin = {"pretty","json:target/jsonReports/cucumber-report.json"},//report generation 
                 features = "src/test/java/features",// till package can be given to run all the feature files
                 glue = {"stepDefinition"})
                 //tags= {"@DeletePlace"})  //package with StepDefitnition files -no need to specify each step definition , Runner and Step definitions should have single parent
public class TestRunner {

}
