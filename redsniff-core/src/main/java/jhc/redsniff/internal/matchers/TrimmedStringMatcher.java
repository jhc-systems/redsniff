package jhc.redsniff.internal.matchers;

import static jhc.redsniff.internal.matchers.MatcherUtil.matchAndDiagnose;
import static jhc.redsniff.internal.matchers.StringMatcher.isString;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matches a string if the target string, when trimmed of whitespace using
 * {@link String#trim()} - is equal to the matched string
 * 
 * @author InfanteN
 * 
 */
public class TrimmedStringMatcher extends CheckAndDiagnoseTogetherMatcher<String> {

    private final String expectedTrimmedString;

    protected TrimmedStringMatcher(String expectedTrimmedString) {
        this.expectedTrimmedString = expectedTrimmedString;
    }

    @Override
    public boolean matchesSafely(String untrimmedActualString, Description mismatchDescription) {
        String actualTrimmedString = untrimmedActualString == null ? null : untrimmedActualString.trim();
        return matchAndDiagnose(isString(expectedTrimmedString), actualTrimmedString, mismatchDescription);
    }

    public void describeTo(Description description) {
        description.appendText("(trimmed) ")
                .appendText(" ")
                .appendValue(expectedTrimmedString);
    }

    @Factory
    public static Matcher<String> trimmedIs(String trimmed) {
        return new TrimmedStringMatcher(trimmed);
    }
}
