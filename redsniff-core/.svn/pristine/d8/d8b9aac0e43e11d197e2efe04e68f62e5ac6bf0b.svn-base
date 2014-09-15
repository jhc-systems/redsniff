package jhc.redsniff.internal.finders;

import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.Item;

import org.hamcrest.Description;

public abstract class WrappingSFinder<E,  C> extends BaseSFinder<E, C> {
    protected final SFinder<E, C> wrappedFinder;

    public WrappingSFinder(SFinder<E, C> wrappedFinder) {
        this.wrappedFinder = wrappedFinder;
    }

    @Override
    public Item<E> findFrom(C context, Description notFoundDescription) {
        return wrappedFinder.findFrom(context, notFoundDescription);
    }

    @Override
    public void describeTo(Description description) {
        wrappedFinder.describeTo(description);
    }
}