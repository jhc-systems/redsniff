package jhc.redsniff.webdriver.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;
import jhc.redsniff.internal.matchers.StringMatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public class TextMatcher extends CheckAndDiagnoseTogetherMatcher<WebElement> {

    private final Matcher<String> stringMatcher;

    TextMatcher(Matcher<String> stringMatcher) {
        this.stringMatcher = stringMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has text ");
        stringMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(WebElement item, Description mismatchDescription) {
        return matchAndDiagnose(stringMatcher, item.getText(), mismatchDescription, "text ");
    }

    @Factory
    public static Matcher<WebElement> hasText(Matcher<String> stringMatcher) {
        return new TextMatcher(stringMatcher);
    }

    @Factory
    public static Matcher<WebElement> hasText(String text) {
        return new TextMatcher(StringMatcher.isString(text));
    }

}
