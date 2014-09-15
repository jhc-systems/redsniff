package jhc.redsniff.internal.expectations;

import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.SelfDescribing;

/**
 * Represents an expectation on a context, about what is found within it. 
 * Typically consists of a {@link jhc.redsniff.core.Finder} and a {@link jhc.redsniff.internal.predicates.Predicate} 
 * on the results found by it.
 * @param <E> the type of element
 * @param <Q> the type of Quantity - either {@link Item} or {@link CollectionOf}
 * @param <C> the type of the context in which we expect to find something
 */
public interface FindingExpectation<E, Q extends Quantity<E>, C>  extends SelfDescribing{
	
	/**
	 * check the expectation for the given context by querying the finder against the context
	 * and checking the predicate is satisfied by the results.
	 * <p> 
	 * Will check at first using optimization on the finder, 
	 * but if not satisfied will run again without optimization, in order to get a message
	 * in terms that the original finder used. 
	 * @param context
	 * @return the result, which indicates whether successful or not if not containing a description of what was wrong
	 */
    ExpectationCheckResult<E, Q> checkFrom(C context);
    
    /**
	 * check the expectation for the given context by querying the finder against the context
	 * and checking the predicate is satisfied by the results.
	 * <p> 
	 * Will only check using optimization on the finder - we just want to know quickly whether the expectation is met or not
	 * (typically used when waiting for something to be true, when we don't want detailed description when it's not)
	 * @param context
	 * @return the result, which indicates whether successful or not if not containing a description of what was wrong
	 */
    ExpectationCheckResult<E, Q> checkFromFailFast(C context);
}