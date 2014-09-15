package jhc.redsniff.internal.finders;

import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;

public abstract class WrappingMFinder<E, C> extends BaseMFinder<E, C> {
    protected final MFinder<E, C> wrappedFinder;

    public WrappingMFinder(MFinder<E, C> wrappedFinder) {
        this.wrappedFinder = wrappedFinder;
    }

    @Override
    public CollectionOf<E> findFrom(C context, Description notFoundDescription) {
        return wrappedFinder.findFrom(context, notFoundDescription);
    }

    @Override
    public void describeTo(Description description) {
        wrappedFinder.describeTo(description);
    }

    
}