package jhc.redsniff.internal.predicates;

import static jhc.redsniff.matchers.numerical.NumericalMatchers.*;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

public class UniqueItem<E> extends SomeNumberOfItems<E> {
    private static final class SingleElementMatcher extends
            TypeSafeMatcher<Integer> implements CustomNumberMatcher {
        @Override
        public void describeTo(Description description) {
            description.appendText("a (unique)");
        }

        @Override
        protected boolean matchesSafely(Integer item) {
            return exactly(1).matches(item);
        }
    }

    protected UniqueItem() {
        super(new SingleElementMatcher());
    }

    @Factory
    public static <E> Predicate<E, CollectionOf<E>> uniqueElement() {
        return new UniqueItem<E>();
    }
}
