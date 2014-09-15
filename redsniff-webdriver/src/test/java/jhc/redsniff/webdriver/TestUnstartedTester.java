package jhc.redsniff.webdriver;

import static jhc.redsniff.webdriver.Finders.div;
import jhc.redsniff.webdriver.RedsniffWebDriverTester;
import jhc.redsniff.webdriver.TestShellImplementations;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestUnstartedTester {
    @Rule
    public ExpectedException thrown = ExpectedException.none().handleAssertionErrors();
    
    @Test
    public void throwsAssertionErrorForAnyAction(){
        thrown.expectMessage("Test not yet started!");
        RedsniffWebDriverTester tester = new TestShellImplementations().getTester();
        tester.find(div());
    }
    
    @Test
    public void throwsAssertionErrorForAssertion(){
        thrown.expectMessage("Test not yet started!");
        RedsniffWebDriverTester tester = new TestShellImplementations().getTester();
        tester.assertPresenceOf(div());
    }
    
    @Test
    public void throwsAssertionErrorForClick(){
        thrown.expectMessage("Test not yet started!");
        RedsniffWebDriverTester tester = new TestShellImplementations().getTester();
        tester.clickOn(div());
    }
}
