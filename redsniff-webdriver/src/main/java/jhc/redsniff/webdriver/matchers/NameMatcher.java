package jhc.redsniff.webdriver.matchers;

import static jhc.redsniff.webdriver.matchers.AttributeMatcher.hasAttribute;
import jhc.redsniff.internal.locators.MatcherLocator;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class NameMatcher extends MatcherByLocator {

    public NameMatcher(Matcher<String> nameMatcher) {
        super(nameMatcher);
    }

    public NameMatcher(String literalAttribute) {
        super(literalAttribute);
    }

    @Override
    public String nameOfAttributeUsed() {
        return "name";
    }

    @Override
    public By getByLocator(String literalName) {
        return By.name(literalName);
    }

    @Override
    public Matcher<WebElement> getWrappedMatcher(Matcher<String> nameMatcher) {
        return hasAttribute("name",  nameMatcher);
    }

    @Factory
    public static MatcherLocator<WebElement, SearchContext> hasName(String value) {
        return new NameMatcher(value);
    }

    @Factory
    public static Matcher<WebElement> hasName(Matcher<String> valueMatcher) {
        return new NameMatcher(valueMatcher);
    }

	@Override
	public int specifity() {
		return 95;
	}

}
