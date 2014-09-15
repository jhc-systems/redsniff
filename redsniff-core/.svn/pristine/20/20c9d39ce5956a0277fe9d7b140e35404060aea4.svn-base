package jhc.redsniff.internal.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class CheckAndDiagnoseTogetherMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

    public CheckAndDiagnoseTogetherMatcher() {
        super();
    }

    protected CheckAndDiagnoseTogetherMatcher(Class<?> expectedType) {
        super(expectedType);
    }

    protected CheckAndDiagnoseTogetherMatcher(ReflectiveTypeFinder typeFinder) {
        super(typeFinder);
    }

    public boolean checkAndDiagnose(T actual, Description mismatchDescription) {
        return matchesSafely(actual, mismatchDescription);
    }

}
