package jhc.redsniff.internal.matchers;

import static jhc.redsniff.internal.matchers.ContainsIgnoringWhitespace.containsIgnoringWhitespace;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;
import jhc.redsniff.internal.matchers.MatcherUtil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

public class CheckAndDiagnoseTogetherMatcherTest {
String itemBeingMatched = "";

    @Test
    public void whenInnerMatchersAreCDTMMatchersMatchAndDiagnosesHappensInOne() {

        final InvocationCounter innerInnerCounter = new InvocationCounter();

        final Matcher<String> innerInnerMatcher = new CheckAndDiagnoseTogetherMatcher<String>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("INNER_INNER_MATCHER");
            }

            @Override
            protected boolean matchesSafely(String item, Description mismatchDescription) {
                innerInnerCounter.increment();
                mismatchDescription.appendText("mismatched");
                return false;
            }
        };
        
        final Matcher<String> innerMatcher = new CheckAndDiagnoseTogetherMatcher<String>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("INNER_MATCHER");
            }

            @Override
            protected boolean matchesSafely(String actual, Description mismatchDescription) {
                return MatcherUtil.matchAndDiagnose(innerInnerMatcher, actual, mismatchDescription, "inner feature ");
            }
        };

        Matcher<String> outerMatcher = matcherDelegatingTo(innerMatcher);
        
        try {
            doAssertion(itemBeingMatched, outerMatcher);
        } catch (Throwable e) {
            assertThat(e.getMessage(), containsIgnoringWhitespace(
                    "Expected: outerMatcherDescription\n" +
                            "but: outer feature had inner feature mismatched"));
        }
        assertThat(innerInnerCounter.getCount(), is(2));
    }

    
    
    private static TypeSafeDiagnosingMatcher<String> matcherDelegatingTo(final Matcher<String> innerMatcher) {
        return new TypeSafeDiagnosingMatcher<String>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("outerMatcherDescription");
            }

            @Override
            protected boolean matchesSafely(String actual, Description mismatchDescription) {
                return MatcherUtil.matchAndDiagnose(innerMatcher, actual, mismatchDescription, "outer feature had ");
            }
        };
    }
    
    @Test
    public void matchesAndDiagnosesSeparately() {

        String itemBeingMatched = "";

        final InvocationCounter innerInnerCounter = new InvocationCounter();

        final Matcher<String> innerInnerMatcher = new TypeSafeDiagnosingMatcher<String>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("INNER_INNER_MATCHER");
            }

            @Override
            protected boolean matchesSafely(String item, Description mismatchDescription) {
                innerInnerCounter.increment();
                mismatchDescription.appendText("mismatched");
                return false;
            }
        };
        final Matcher<String> innerMatcher = new TypeSafeDiagnosingMatcher<String>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("INNER_MATCHER");
            }

            @Override
            protected boolean matchesSafely(String actual, Description mismatchDescription) {
                return MatcherUtil.matchAndDiagnose(innerInnerMatcher, actual, mismatchDescription, "inner feature ");
            }
        };

        Matcher<String> outerMatcher = matcherDelegatingTo(innerMatcher);
        try {
            doAssertion(itemBeingMatched, outerMatcher);
        } catch (Throwable e) {
            assertThat(e.getMessage(), containsIgnoringWhitespace(
                    "Expected: outerMatcherDescription\n" +
                            "but: outer feature had inner feature mismatched"));
        }
        assertThat(innerInnerCounter.getCount(), is(8));
    }

    private void doAssertion(String itemBeingMatched, Matcher<String> outerMatcher) {
        assertThat(itemBeingMatched, outerMatcher);
    }

    private static class InvocationCounter {
        private int count;

        public int getCount() {
            return count;
        }

        public void increment() {
            count++;
        }
    }

}
