package jhc.redsniff.internal.finders;

import static jhc.redsniff.core.FindingExpectations.expectationOf;
import static jhc.redsniff.internal.core.Item.oneOf;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import static jhc.redsniff.matchers.numerical.NumericalMatchers.*;
import java.util.Iterator;

import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.predicates.SomeNumberOfItems;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class OrdinalFinder<E, C> extends BaseSFinder<E, C>  {

	private final int ordinalNumber;
	private final int absOrdinalNumber;
	private MFinder<E, C> innerFinder;
	
	public OrdinalFinder(int ordinalNumber,MFinder<E, C> innerFinder) {
		if(ordinalNumber==0)
			throw new AssertionError("ordinal index cannot be zero");
		this.ordinalNumber = ordinalNumber;
		this.innerFinder = innerFinder;
		this.absOrdinalNumber= Math.abs(ordinalNumber);
	}
	
	@Override
	public Item<E> findFrom(C context, Description notFoundDescription) {
		ExpectationCheckResult<E, CollectionOf<E>> result = 
		        checkerFor(context).resultOfChecking(expectationOf(SomeNumberOfItems.<E>aNumberOfElements(atLeast(absOrdinalNumber)), innerFinder));
		if(result.meetsExpectation()) {
			return nthElement(result.foundQuantity());
		} else {
			if(result.foundQuantity().size() > 0 )
				notFoundDescription.appendText("only "); //for 'only found x such elements'
			
			result.describeTo(notFoundDescription);
			return Item.oneOf(null);
		}
	}

	@Override
	public void describeTo(Description description) {
		description
		.appendText(ordinal() + " " )
		.appendDescriptionOf(innerFinder);
	}


	private Item<E> nthElement(CollectionOf<E> foundElements) {
		Iterator<E> iterator = foundElements.iterator();
		int indexFromStart = indexFromStart(foundElements.size());
		E element = null;
		for(int i=1;i<=indexFromStart;i++)
			element = iterator.next();
		return oneOf(element);
	}

	private int indexFromStart(int size) {
		if(ordinalNumber>0)
			return ordinalNumber;
		else {
			int numberFromEnd = absOrdinalNumber -1;
			return size-numberFromEnd;
		}
	}

	private String ordinal() {
		return ordinalNumber>0 ? positiveNthOrdinal() : ordinalFromLast();
	}

	private String ordinalFromLast() {
		return absOrdinalNumber==1?
				"last"
				:positiveNthOrdinal() + " last";
	}

	private String positiveNthOrdinal() {
		String suffix = "";
		int last2Digits = ordinalNumber % 100;
		boolean isTeen= last2Digits/10==1;
		suffix="th";
		if(!isTeen) {
			switch(ordinalNumber % 10) {
			case 1:
				suffix="st";break;
			case 2: 
				suffix="nd";break;
			case 3:
				suffix="rd";break;
			}
		}
		return ordinalNumber +suffix;
	}
	
	
	/**
	 * Creates a finder for finding the nth item found by the wrapped finder.  If less than n elements are found that is included in the diagnostics
	 * @param n
	 * @param finder - the finder to get the nth E element from context of type C
	 * @return 
	 */
	@Factory
	public static <E, C> SFinder<E, C> nth(int n, MFinder<E, C> finder){
		return new OrdinalFinder<E, C>(n,finder);
	}
	
	/**
	 * Creates a finder for finding the first item found by the wrapped finder.  If no elements are found by the finder then that is included in the diagnostics
	 * @param finder - the finder to get the first element of
	 * @return 
	 */
	@Factory
	public static <E, C> SFinder<E, C> first(MFinder<E, C> finder){
		return nth(1,finder);
	}
	
	/**
	 * @see #nth(int, MFinder)
	 * @param finder
	 * @return
	 */
	@Factory
	public static <E, C> SFinder<E, C> second(MFinder<E, C> finder){
		return nth(2,finder);
	}
	
	/**
	 * @see #nth(int, MFinder)
	 * @param finder
	 * @return
	 */
	@Factory
	public static <E, C> SFinder<E, C> third(MFinder<E, C> finder){
		return nth(3,finder);
	}
	
	/**
	 * @see #nth(int, MFinder)
	 * @param finder
	 * @return
	 */
	@Factory
	public static <E, C> SFinder<E, C> fourth(MFinder<E, C> finder){
		return nth(4,finder);
	}
	
	/**
	 * @see #nth(int, MFinder)
	 * @param finder
	 * @return
	 */
	@Factory
	public static <E, C> SFinder<E, C> fifth(MFinder<E, C> finder){
		return nth(5,finder);
	}
	
	/**
	 * @see #nth(int, MFinder)
	 * @param finder
	 * @return
	 */
	@Factory
	public static <E, C> SFinder<E, C> last(MFinder<E, C> finder){
		return nth(-1,finder);
	}

	@Override
	public SFinder<E, C> asOptimizedFinder() {
		return new OrdinalFinder<>(ordinalNumber, innerFinder.asOptimizedFinder());
	}
}
