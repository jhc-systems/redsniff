package jhc.redsniff.webdriver.matchers;


import static jhc.redsniff.webdriver.matchers.AttributeMatcher.hasAttribute;
import jhc.redsniff.internal.locators.MatcherLocator;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class IdMatcher extends MatcherByLocator{

	public IdMatcher(Matcher<String> idMatcher) {
		super(idMatcher);
	}

	public IdMatcher(String id) {
		super(id);
	}
	
	@Override
	public Matcher<WebElement> getWrappedMatcher(Matcher<String> idMatcher) {
		return hasAttribute("id","id", idMatcher);
	}
	
	@Override
	public By getByLocator(String literalId) {
		return By.id(literalId);
	}

	@Factory
	public static MatcherLocator<WebElement, SearchContext> hasId(String value){
		return new IdMatcher(value);
	}
	
	@Factory
	public static Matcher<WebElement> hasId(Matcher<String> valueMatcher){
		return new IdMatcher(valueMatcher);
	}

	@Override
	public String nameOfAttributeUsed() {
		return "id";
	}

	@Override
	public int specifity() {
		return 99;
	}

	
}