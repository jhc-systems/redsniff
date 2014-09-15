package jhc.redsniff.webdriver.finders;

import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.finders.BaseMFinder;

import org.hamcrest.Description;
import org.hamcrest.Factory;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class OrFinder<E, C> extends BaseMFinder<E, C> {

    private final MFinder<E, C> finder1;
    private final MFinder<E, C> finder2;

    public OrFinder(MFinder<E, C> finder1, MFinder<E, C> finder2) {
        this.finder1 = finder1;
        this.finder2 = finder2;
    }

    @Factory
    public static <FE, FC> OrFinder<FE, FC> or(MFinder<FE,FC> finder1, MFinder<FE,FC> finder2) {
        return new OrFinder<FE, FC>(finder1, finder2);
    }

    @Override
    public void describeTo(Description description) {
      description.appendText(this.finder1.toString() + ", " + this.finder2.toString());
    }

    // TODO move to utility class
    private static <T> CollectionOf<T> concat(CollectionOf<T> a, CollectionOf<T> b) {
        if (a.isEmpty()) {
            return b;
        } else if (b.isEmpty()) {
            return a;
        } else {
            return new CollectionOf<T>(Lists.newArrayList(Iterables.concat(a, b)));
        }
    }

    @Override
    public CollectionOf<E> findFrom(C context,
            Description notFoundDescription) {
        CollectionOf<E> result1 = finder1.findFrom(context, notFoundDescription);
        CollectionOf<E> result2 = finder2.findFrom(context, notFoundDescription);
        return concat(result1, result2);
    }

    @Override
    public MFinder<E, C> asOptimizedFinder() {
        return this;
    }
}
