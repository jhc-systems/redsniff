package jhc.redsniff.webdriver.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import static jhc.redsniff.internal.matchers.StringMatcher.isString;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

/**
 *
 */
public final class AttributeMatcher extends CheckAndDiagnoseTogetherMatcher<WebElement> {

    private final Matcher<String> stringMatcher;
    private final String attribute;
    private final String attributeDescription;

    private AttributeMatcher(String attribute, Matcher<String> stringMatcher) {
        this(attribute, attribute, stringMatcher);
    }

    private AttributeMatcher(String attribute, String attributeDescription, Matcher<String> stringMatcher) {
        this.stringMatcher = stringMatcher;
        this.attribute = attribute;
        this.attributeDescription = attributeDescription;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has " + (attributeDescription == null ? attribute : attributeDescription) + " ");
        stringMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(WebElement actual, Description mismatchDescription) {

        String attributeValue = actual.getAttribute(attribute);
        if(attributeValue==null){
            mismatchDescription.appendText("did not have a " + (attributeDescription == null ? attribute : attributeDescription + "(" + attribute +")"));
            return false;
        }
            
        return matchAndDiagnose(stringMatcher, attributeValue, mismatchDescription, attribute + " ");
    }

    @Factory
    public static Matcher<WebElement> hasAttribute(String attribute, Matcher<String> stringMatcher) {
        return new AttributeMatcher(attribute, stringMatcher);
    }

    @Factory
    public static Matcher<WebElement> hasAttribute(String attribute, String attributeDescription,
            Matcher<String> stringMatcher) {
        return new AttributeMatcher(attribute, attributeDescription, stringMatcher);
    }

    @Factory
    public static Matcher<WebElement> hasAttribute(String attribute, String text) {
        return hasAttribute(attribute, isString(text));
    }

    @Factory
    public static Matcher<WebElement> hasValue(String value) {
        return hasValue(isString(value));
    }

    @Factory
    public static Matcher<WebElement> hasValue(Matcher<String> valueMatcher) {
        return hasAttribute("value", valueMatcher);
    }

}
