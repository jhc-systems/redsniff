package jhc.redsniff.internal.locators;

import jhc.redsniff.core.MFinder;

public interface LocatorSelectionStrategy<E,C> {
//TODO
    public MFinder<E, C> getOptimizedFinder();
}
