package jhc.redsniff.internal.predicates;


import static jhc.redsniff.core.Describer.describable;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.Factory;
public class NoItems<E> implements Predicate<E, CollectionOf<E>> {

	@Override
	public void describePredicateOnFinder(Finder<E, CollectionOf<E>, ?> finder, Description description) {
		description
			.appendText("not to find any ") 
			.appendDescriptionOf(finder);
	}

	@Override
	public boolean isSatisfiedBy(CollectionOf<E> foundElements) {
		return foundElements.isEmpty();
	}

	@Override
	public void diagnoseNotSatisfyingTo(
			Description dissatisfactionDescription, 
			CollectionOf<E> foundElements, Description notFoundDescription) {
		dissatisfactionDescription
			.appendText("did find:")
			.appendDescriptionOf(describable(foundElements));
	}

	@Factory
	public static <E> Predicate<E, CollectionOf<E>> noElements(){
		return new NoItems<E>();
	}
}
