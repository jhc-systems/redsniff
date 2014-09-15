package jhc.redsniff.action;

import org.hamcrest.Description;

import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Quantity;

/**
 * A {@link Finder} decorator that can be used in an action such as clicking.
 * Can perform the action on every item found or on a unique item
 *
 * @param <E>
 * @param <Q>
 * @param <C>
 */
public interface  ActionableFinder<E, Q extends Quantity<E>, C> extends Finder<E, Q, C> {
	
	/**
	 * Take the given performer and have it perform its action on the element(s) found by this finder
	 * @param performer
	 * @param someOfItem
	 */
	void performAction(ActionPerformer<E> performer, Q someOfItem );
	
	/**
	 * Describe what we will perform the actions on - e.g. each element found
	 * @param description
	 */
	void describeTypeOfActionTo(Description description);
}
