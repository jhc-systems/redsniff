package jhc.redsniff.internal.core;

import java.util.Collection;

import jhc.redsniff.action.ActionDriver;
import jhc.redsniff.action.Controller;
import jhc.redsniff.core.Finder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.expectations.AlwaysFindsMultipleElementsChecker;
import jhc.redsniff.internal.finders.within.SFinderWithinMFinder;

/**
 * Specific type of {@link RedsniffTester} whose {@link #find(Finder)} method always returns a collection of elements, even when passed an {@link SFinder}
 * used by {@link #inEach(jhc.redsniff.core.MFinder)}  so that e.g. <tt>t.inEach(row() ).find(only(checkbox())</tt> will return multiple checkboxes - one for each 
 * of the multiple rows
 *
 * @param <E>
 * @param <C>
 */
public class AlwaysFindsMultipeElementsTester<E, C> extends RedsniffTester<E, C> {

	private final AlwaysFindsMultipleElementsChecker<C> checker;

	public AlwaysFindsMultipeElementsTester(AlwaysFindsMultipleElementsChecker<C> checker, Controller<E> controller, ActionDriver<E, C> actionDriver) {
		super(checker, controller, actionDriver);
		this.checker = checker;
	}

	@Override
	public <I, T, Q extends TypedQuantity<I, T>> Collection<I> find(
			Finder<I, Q, C> finder) {
		return checker.find(finder);
	}
}
