package jhc.redsniff.internal.predicates;

import static jhc.redsniff.core.Describer.describable;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Item;

import org.hamcrest.Description;
import org.hamcrest.Factory;

public class NoItem<E> implements Predicate<E, Item<E>> {

    @Override
    public boolean isSatisfiedBy(Item<E> foundElements) {
       return foundElements.get()==null;
    }

    @Override
    public void diagnoseNotSatisfyingTo(Description dissatisfactionDescription,
            Item<E> foundElement, Description notFoundDescription) {
        dissatisfactionDescription
        .appendText("did find:")
        .appendDescriptionOf(describable(foundElement));
    }

    @Override
    public void describePredicateOnFinder(Finder<E, Item<E>, ?> finder, Description description) {
        description
        .appendText("not to find any ") 
        .appendDescriptionOf(finder);
    }
    
    @Factory
    public static <E> Predicate<E, Item<E>> nothing(){
        return new NoItem<E>();
    }

}
