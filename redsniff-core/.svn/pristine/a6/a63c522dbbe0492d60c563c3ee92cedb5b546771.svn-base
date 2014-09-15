package jhc.redsniff.internal.predicates;

import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class SomeItemsForPurpose<E> extends SomeItem<E> {

    private final String purpose;

    protected SomeItemsForPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public void describePredicateOnFinder(Finder<E, CollectionOf<E>, ?> finder, Description description) {
        super.describePredicateOnFinder(finder, description);
        description
                .appendText(" to " + purpose);

    }

    @Factory
    public static <E> Predicate<E, CollectionOf<E>> elementTo(String purpose) {
        return new SomeItemsForPurpose<E>(purpose);
    }
}
