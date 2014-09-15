package jhc.redsniff.internal.matchers;


import jhc.redsniff.internal.util.StringDiffFormatter;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * Diagnosing String Matcher which uses Comparison Compactor from junit
 * 
 */
public class StringMatcher extends CheckAndDiagnoseTogetherMatcher<String> {

    private final String expected;

    public StringMatcher(String string) {
        this.expected = string;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("\"" + expected + "\"");
    }

    @Override
    protected boolean matchesSafely(String actual, Description mismatchDescription) {
        boolean matches = Matchers.is(expected).matches(actual);
        if (!matches) {
        	String diff = new StringDiffFormatter().formatDifference(expected, actual, "was:\n   '%1$s'\ninstead of:\n   '%2$s'");
        	mismatchDescription.appendText(diff);
		}
        return matches;
    }

    @Factory
    public static Matcher<String> isString(String expected) {
        return new StringMatcher(expected);
    }
}
