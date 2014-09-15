package jhc.redsniff.internal.matchers;

import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;

import org.hamcrest.Description;

//TODO NOt yet doing anything useful
@SuppressWarnings("unused")
public class FoundElementsAre<C, E extends C> extends CheckAndDiagnoseTogetherMatcher<E> {

    private final MFinder<E, C> childFinder;
   
    private final MFinder<E, C>[] expectedFindings;

    public FoundElementsAre(MFinder<E, C> childFinder, MFinder<E, C>[] expectedFindings) {
        this.childFinder = childFinder;
        this.expectedFindings = expectedFindings;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("FINDS SAME ELEMENTS");
    }
    // TODO
    @Override
    protected boolean matchesSafely(E candidateElement,
            Description mismatchDescription) {
        ExpectationCheckResult<E, CollectionOf<E>> result = checkerFor((C)candidateElement).resultOfChecking(expectationOfSome(childFinder));
        result.foundQuantity();
        return false;
    }

}
