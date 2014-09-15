package jhc.redsniff.internal.predicates;

import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Item;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class SomeItemForPurpose<E> extends ItemPresent<E> {
    private final String purpose;

    protected SomeItemForPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public void describePredicateOnFinder(Finder<E, Item<E>, ?> finder, Description description) {
        super.describePredicateOnFinder(finder, description);
        description
            .appendText(" to " + purpose);
    }

    @Factory
    public static <E> Predicate<E, Item<E>> elementTo(String purpose) {
        return new SomeItemForPurpose<E>(purpose);
    }
}
