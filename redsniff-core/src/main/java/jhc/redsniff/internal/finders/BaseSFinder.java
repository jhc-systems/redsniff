package jhc.redsniff.internal.finders;

import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.finders.within.SFinderWithinMFinder;
import jhc.redsniff.internal.finders.within.SFinderWithinSFinder;

public abstract class BaseSFinder<E, C>implements SFinder<E, C> {

	@Override
	public <OE extends C, OC> SFinder<E, OC> within(SFinder<OE, OC> scopeFinder) {
	    return SFinderWithinSFinder.finderWithin(this, scopeFinder);
	}

	@Override
	public <OE extends C, OC> SFinderWithinMFinder<E,C, OE, OC> withinEach(MFinder<OE, OC> scopeFinder) {
	    return new SFinderWithinMFinder<E,C, OE, OC>(this, scopeFinder);
	}

	@Override
	public String toString() {
		return Describer.asString(this);
	}
}