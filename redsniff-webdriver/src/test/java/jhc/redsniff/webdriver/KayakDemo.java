package jhc.redsniff.webdriver;

import static jhc.redsniff.webdriver.Finders.*;
import static jhc.redsniff.webdriver.Finders.textbox;
import static jhc.redsniff.webdriver.Sugar.into;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasCssClass;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasId;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasName;
import static jhc.redsniff.webdriver.WebDriverMatchers.hasText;
import static jhc.redsniff.webdriver.WebDriverMatchers.isDisplayed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.finders.TransformingMFinder;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@Ignore
public class KayakDemo {

	RedsniffWebDriverTester t = new RedsniffWebDriverTester(new ChromeDriver());
	
	
	@Test
	public void testName() throws Exception {
		t.goTo("http://www.kayak.co.uk");
		t.clickOn($("label").that(hasText("One-way")));
		t.type("London ", into($("#origin")));
		t.waitFor(smartBox().that(isDisplayed()));
		t.clickOn(airportListItem("Stansted"));
		t.type("Paris", into($("#destination")));
		t.clickOn(airportListItem("Orly"));
		RedsniffWebDriverTester cmpSection = t.newTesterFrom(t.find(only($("#compareToCheckboxes"))));
//		t.inThe($("#compareToCheckboxes")).assertThatThe(attribute("title").withinEach(span().that(hasCssClass("cmp2item")).that(isDisplayed()))),
//				contains("Opodo", "Expedia","Bravofly"));
	}


	private static MFinder<WebElement, SearchContext> airportListItem(
			String airportName) {
		return listItem().that(hasText(containsString(airportName))).withinThe(smartBox().that(isDisplayed()));
	}


	private static MFinder<WebElement, SearchContext> smartBox() {
		return $("#smartbox");
	}
}
