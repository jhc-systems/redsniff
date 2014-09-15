package jhc.redsniff.core;

import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Matcher;


/**
 * A {@link Finder} for which there could be multiple results. 
 * The {@link Finder#findFrom(Object, org.hamcrest.Description)} method will, when successful, return a {@link Collection} of elements rather than a single element.
 * Most {@link Finder} implementations will typically descend from this.
 * Implementations should be immutable.
 *
 * @param <E> the type of the elements the Finder finds
 * @param <C> the type of the context in which the Finder searches
 */
public interface MFinder<E, C> extends Finder<E,CollectionOf<E>, C> {
	/**
	 * Takes a Matcher<E> to add as a filter and returns a new {@link MFinder} with that filter applied. 
	 * eg adding <tt>.that( hasName("bob") )</tt> will return a Finder based on this one but which will only find elements that have name "bob".
	 * @param elementMatcher a hamcrest {@link Matcher}. The returned finder's findFrom method will return only those elements who match for this matcher.
	 * @return A {@link MFinder} based on this one but with the given matcher applied as a filter
	 */
    MFinder<E, C> that(Matcher<? super E> elementMatcher);
    
    /**
     * Takes another MFinder expression and returns a finder which will use the results of that finder as contexts for this finder.
     * e.g. <tt>button().withinA( div() )</tt> will give a finder that searches for only those buttons within div elements
     * @param outerFinder the outer finder with which to search for a context
     * @param <OC> the type of the context used by the outerFinder
     * @param <OE> the type of the element found by the outerFinder. this must be a subtype of the type of context (<C>) used by this finder.
     * @return a new {@link MFinder} that performs a transitive search 
     */
	<OE extends C, OC > MFinder<E, OC> withinA(MFinder<OE, OC> outerFinder);
	
	 /**
     * Takes an SFinder expression and returns a finder which will use the result of that finder as context for this finder.
     * e.g. <tt>button().withinThe( only( div() ) )</tt> will give a finder that searches for only those buttons within the unique div element
     * @param outerFinder the outer finder with which to search for a context
     * @param <OC> the type of the context used by the outerFinder
     * @param <OE> the type of the element found by the outerFinder. this must be a subtype of the type of context (<C>) used by this finder.
     * @return a new finder that performs a transitive search
     */
	<OE extends C, OC > MFinder<E, OC> withinThe(SFinder<OE, OC> finder);
	
	/**
     * Takes an SFinder expression and returns a finder which will use the result of that finder as context for this finder.
     * e.g. <tt>button().withinThe( only( div() ) )</tt> will give a finder that searches for only those buttons within the unique div element
     * @param outerFinder the outer finder with which to search for a context
     * @param <OC> the type of the context used by the outerFinder
     * @param <OE> the type of the element found by the outerFinder. this must be a subtype of the type of context (<C>) used by this finder.
     * @return a new finder that performs a transitive search
     */
	<OE extends C, OC > MFinder<E, OC> withinThe(MFinder<OE, OC> finder);
	
	/**
	 * Used internally to optimize composite finders. May be removed from this interface shortly.
	 * @return a restructured composite {@link MFinder} with the same criteria but optimized.
	 */
	MFinder<E, C> asOptimizedFinder();//TODO - could these be hidden from users somehow?
	
	/**
	 * Used internally to optimize composite finders with matchers applied
	 * @return a restructured composite {@link MFinder} with the same criteria but optimized.
	 */
	MFinder<E, C> optimizedWith(Matcher<? super E> matcher);
}
