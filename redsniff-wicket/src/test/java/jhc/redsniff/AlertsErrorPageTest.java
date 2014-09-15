package jhc.redsniff;

import static jhc.redsniff.webdriver.Finders.div;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasName;
import jhc.redsniff.wicket.WicketEnabledTester;

import org.junit.Before;
import org.junit.Test;

public class AlertsErrorPageTest extends RedsniffWicketTestBase {

	@Override
	@Before
	public void setup() {
		t = new WicketEnabledTester(getWebDriver());
		gotoTestPage();
	}

	@Override
	public String getTestPage() { 
		return getClass().getSimpleName() + ".html";
	}
	
	@Test
	public void complainsAndShowsErrorPageStackTraceWhenErrorPageFoundWhileLookingForSomethingElse(){
		thrown.expectMessage("While expecting: a div that has name \"some-expected-div\"\n" +
				"Got wicket error page:");
		thrown.expectMessage("WicketMessage: Can't instantiate page using constructor public jhc.webapps.figaro.test.utils.TestPage()\n"
							+"Root cause:\n"
							+"java.lang.AssertionError: jhc.webapps.figaro.FigaroWebException");
		t.assertPresenceOf(div().that(hasName("some-expected-div")));
	}

	
}
