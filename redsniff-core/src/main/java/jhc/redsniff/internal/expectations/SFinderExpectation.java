package jhc.redsniff.internal.expectations;

import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.predicates.Predicate;

public class SFinderExpectation<E, C> extends  BaseFindingExpectation<E, Item<E>, C> {

    public SFinderExpectation(Predicate<E, Item<E>> expectation, SFinder<E, C> finder) {
        super(expectation, finder);
    }
    
}
