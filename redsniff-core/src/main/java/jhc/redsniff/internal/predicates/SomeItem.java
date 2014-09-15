package jhc.redsniff.internal.predicates;

import static jhc.redsniff.matchers.numerical.NumericalMatchers.*;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

public class SomeItem<E> extends SomeNumberOfItems<E> {

    protected SomeItem() {
        super(new AnElementMatcher());
    }

    private static final class AnElementMatcher extends TypeSafeMatcher<Integer> implements CustomNumberMatcher {
        @Override
        public void describeTo(Description description) {
            description.appendText("a");
        }

        @Override
        protected boolean matchesSafely(Integer item) {
            return atLeast(1).matches(item);
        }
    }

    @Factory
    public static <E> Predicate<E, CollectionOf<E>> someElementPresent() {
        return new SomeItem<E>();
    }
}
