package jhc.redsniff.webdriver.matchers;

import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public class CssRenderingMatcher extends ElementStateMatcher<String> {

    private final String cssAttributeName;

    public CssRenderingMatcher(String cssAttributeName, Matcher<String> subMatcher) {
        super(subMatcher, "css rendering of '" + cssAttributeName + "'", "css rendering of '" + cssAttributeName + "'");
        this.cssAttributeName = cssAttributeName;
    }

    @Override
    protected String stateOf(WebElement actual) {
        return actual.getCssValue(cssAttributeName);
    }

    @Factory
    public static Matcher<WebElement> renderedWith(String cssAttributeName, Matcher<String> cssAttributeMatcher) {
        return new CssRenderingMatcher(cssAttributeName, cssAttributeMatcher);
    }

    @Factory
    public static Matcher<WebElement> renderedWith(String cssAttributeName, String cssAttributeValue) {
        return new CssRenderingMatcher(cssAttributeName, is(isString(cssAttributeValue)));
    }

}
