package jhc.redsniff.core;

import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.RedsniffTester;
import jhc.redsniff.internal.finders.OnlyFinder;
import jhc.redsniff.internal.finders.within.SFinderWithinMFinder;

/**
 * A {@link Finder} for which there can only be either one or no results.
 * The {@link Finder#findFrom(Object, org.hamcrest.Description)} method will, when successful, return a {@link Item} containing the element.
 * When used by {@link RedsniffTester#find(Finder)} it will return the found element itself, or null.
 * 
 * Typically created by internal methods such as {@link OnlyFinder#only(Finder)} rather than implemented directly by clients.
 * Any implementations should be immutable.
 * @author Nic
 *
 * @param <E> the type of the elements the Finder finds
 * @param <C> the type of the context in which the Finder searches
 */
public interface SFinder<E, C> extends Finder<E, Item<E>, C> {

	 /**
     * Takes another SFinder expression and returns a finder which will use the result of that finder as context for this finder.
     * e.g. <tt>only(button()).withinThe( only( div() ) )</tt> will give a finder that searches for only those buttons within the unique div element
     * @param outerFinder the outer finder with which to search for a context
     * @param <OC> the type of the context used by the outerFinder
     * @param <OE> the type of the element found by the outerFinder. this must be a subtype of the type of context (<tt>&lt;C&gt;</tt>) used by this finder.
     * @return a new finder that performs a transitive search
     */
    <OE extends C, OC> SFinder<E, OC> within(SFinder<OE, OC> outerFinder);
    
    /**
     * Takes an MFinder expression and returns a finder which will use each result of that finder as a context for this finder and combine the results. 
     * Will return as many results as returned by the outerFinder, since <b>each</b> result of that will be searched for this finder.
     * <p>e.g. <tt>first(button()).withinEach( div() )</tt> will give a finder that searches for the first button in <b>each</b> of the (possibly multiple) divs. 
     * (if any one of those divs does not contain a button then no results will be returned at all)
     * @param outerFinder the outer finder with which to search for a context
     * @param <OC> the type of the context used by the outerFinder
     * @param <OE> the type of the element found by the outerFinder. this must be a subtype of the type of context (<tt>&lt;C&gt;</tt>) used by this finder.
     * @return a new finder that performs a transitive search
     */
    <OE extends C, OC> SFinderWithinMFinder<E, C, OE, OC> withinEach(MFinder<OE, OC> finder);
    
    /**
	 * Used internally to optimize composite finders. May be removed from this interface shortly.
	 * @return a restructured composite {@link SFinder} with the same criteria but optimized.
	 */
    SFinder<E, C> asOptimizedFinder();
}
