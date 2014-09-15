package jhc.redsniff.core;


import jhc.redsniff.action.ActionPerformer;
import jhc.redsniff.action.ActionableFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.predicates.ItemPresent;
import jhc.redsniff.internal.predicates.NoItem;
import jhc.redsniff.internal.predicates.NoItems;
import jhc.redsniff.internal.predicates.Predicate;
import jhc.redsniff.internal.predicates.SingleItemMatching;
import jhc.redsniff.internal.predicates.SomeItem;
import jhc.redsniff.internal.predicates.SomeItemForPurpose;
import jhc.redsniff.internal.predicates.SomeItemsForPurpose;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public final class FindingPredicates {

    private FindingPredicates(){}
    
    /**
     * Checks that the number of elements in the collection satisfies the numerical matcher(cardinality constraint)
     * eg - at least 4 elements
     * @param cardinalityConstraint
     * @param clazz - the class of element - for generic resolution 
     * @return
     */
	public static <E> jhc.redsniff.internal.predicates.Predicate<E, CollectionOf<E>> aNumberOfElements(org.hamcrest.Matcher<java.lang.Integer> cardinalityConstraint, Class<E> clazz) {
		return jhc.redsniff.internal.predicates.SomeNumberOfItems.<E>aNumberOfElements(cardinalityConstraint);
	}

	/**
	 * Checks the collection is not empty
	 * @param clazz - the class of element - for generic resolution
	 * @return
	 */
	public static <E> jhc.redsniff.internal.predicates.Predicate<E, CollectionOf<E>> someElementPresent(Class<E> clazz) {
		return jhc.redsniff.internal.predicates.SomeItem.<E>someElementPresent();
	}

	/**
	 * Checks for exactly one element, using the word "a unique" to describe it
	 * @param clazz
	 * @return
	 */
	public static <E> jhc.redsniff.internal.predicates.Predicate<E, CollectionOf<E>> uniqueElement(Class<E> clazz) {
		return jhc.redsniff.internal.predicates.UniqueItem.<E>uniqueElement();
	}

	/**
	 * Checks for an empty collection
	 * @param clazz
	 * @return
	 */
	public static <E> jhc.redsniff.internal.predicates.Predicate<E, CollectionOf<E>> noElements(Class<E> clazz) {
		return jhc.redsniff.internal.predicates.NoItems.<E>noElements();
	}


	/**
	 * Predicate expecting no results, using the type of quantity relevant to the class of finder passed
	 * 
	 * @param clazz - the class of {@link Finder} 
	 * @return If {@code clazz} is an {@link MFinder}, a predicate on an empty collection, otherwise on a null single {@link Item}
	 */
	@SuppressWarnings("unchecked")
	public static < E,  Q extends Quantity<E>, F extends Finder<E, Q, ?>> Predicate<E,Q> absence(Class<F> clazz){
		if(MFinder.class.isAssignableFrom(clazz))
			return (Predicate<E, Q>) NoItems.<E> noElements();
		else
			return (Predicate<E, Q>) NoItem.<E>nothing();
	}

    /**
     * Predicate expecting some result, using the type of quantity relevant to the class of finder passed
     * 
     * @param clazz - the class of {@link Finder} 
     * @return If {@code clazz} is an {@link MFinder}, a predicate on a collection with some elements, otherwise on a populated single {@link Item}
     */	
	@SuppressWarnings("unchecked")
	public static < E,  Q extends Quantity<E>, F extends Finder<E, Q, ?>> Predicate<E,Q> presence(Class<F> clazz){
		if(MFinder.class.isAssignableFrom(clazz))
			return (Predicate<E, Q>) SomeItem.<E> someElementPresent();
		else
			return (Predicate<E, Q>) ItemPresent.<E> someElement();
	}

	/**
	 * Predicate on a single {@link Item} , that it matches the given matcher
	 * @param matcher
	 * @return
	 */
	public static <E> Predicate<E, Item<E>> singleItemMatches(Matcher<? super E> matcher) {
		return new SingleItemMatching<E>(matcher);
	}

	/**
	 * Predicate on presence of an element, that will be used for the given purpose, eg clicking. Includes the description of action.
	 * @param performer - the action performer the target item(s) are intended for
	 * @param clazz - the class of finder, to determine the quantity type
	 * @return 
	 */
    public static <E, Q extends Quantity<E>> Predicate<E, Q> actionPredicate(ActionPerformer<E> performer, ActionableFinder<E, Q, ?> actionableFinder) {
    	Description purposeDescription = Describer.newDescription().appendDescriptionOf(performer);
    	actionableFinder.describeTypeOfActionTo(purposeDescription);
    	return forPurposePredicate(purposeDescription.toString(), actionableFinder);
    }

    /**
     *   
     * @param purpose
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static < E,  Q extends Quantity<E>> Predicate<E,Q> forPurposePredicate(String purpose, Finder<E, Q, ?> actionableFinder){ 
        if(actionableFinder instanceof MFinder)
    		return (Predicate<E, Q>)  SomeItemsForPurpose.<E>elementTo(purpose);
    	else
    		return (Predicate<E, Q>)  SomeItemForPurpose.<E>elementTo(purpose);
    }
    
    
}
