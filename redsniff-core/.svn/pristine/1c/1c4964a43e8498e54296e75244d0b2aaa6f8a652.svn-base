package jhc.redsniff.internal.matchers;

import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class HasSubElement<E, Q extends Quantity<E>, C> extends CheckAndDiagnoseTogetherMatcher<C> {

    private final Finder<E, Q, C> childFinder;

    public HasSubElement(Finder<E, Q, C> childFinder) {
        this.childFinder = childFinder;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(" contains : {a ")
                .appendDescriptionOf(childFinder).appendText("}");
    }

    @Override
    protected boolean matchesSafely(C candidateElement, Description mismatchDescription) {
        ExpectationCheckResult<E, Q> result = checkerFor(candidateElement).resultOfChecking(expectationOfSome(childFinder));
        boolean matches = result.meetsExpectation();
        if (!matches)
            mismatchDescription
                    .appendText("child elements did not match:\n")
                    .appendDescriptionOf(result);
        return matches;

    }

    @Factory
    public static <E, Q extends Quantity<E>, C> Matcher<C> hasSubElement(Finder<E, Q, C> subElementFinder) {
        return new HasSubElement<E, Q, C>(subElementFinder);
    }
}
