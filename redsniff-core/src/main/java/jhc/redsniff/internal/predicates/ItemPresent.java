package jhc.redsniff.internal.predicates;

import static jhc.redsniff.core.Describer.concat;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Item;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class ItemPresent<E> implements Predicate<E, Item<E>> {

	@Override
	public boolean isSatisfiedBy(Item<E> foundElement) {
		return foundElement.hasAmount();
	}

	@Override
	public void diagnoseNotSatisfyingTo(
			Description dissatisfactionDescription, 
			Item<E> foundElement, Description notFoundDescription) {
		concat(dissatisfactionDescription, notFoundDescription);
	}

	@Override
	public void describePredicateOnFinder(Finder<E,Item<E>, ?> finder,
			Description description) {
		description.appendText("a ").appendDescriptionOf(finder);	
	}
	
	@Factory
	public static <E> Predicate<E, Item<E>> someElement(){
		return new ItemPresent<E>();
	}

}
