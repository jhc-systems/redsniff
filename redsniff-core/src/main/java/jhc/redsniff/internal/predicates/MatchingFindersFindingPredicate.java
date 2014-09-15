package jhc.redsniff.internal.predicates;

import java.util.List;

import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
//TODO
public class MatchingFindersFindingPredicate<E, C> implements Predicate<E, Quantity<E>>{
    
    public MatchingFindersFindingPredicate(List<MFinder<E, C>> findings){
        
    }
    
    @Override
    public boolean isSatisfiedBy(Quantity<E> foundElements) {
        return false;
    }

    @Override
    public void diagnoseNotSatisfyingTo(Description dissatisfactionDescription, Quantity<E> foundElements,
            Description notFoundDescription) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void describePredicateOnFinder(Finder<E, Quantity<E>, ?> finder, Description description) {
        // TODO Auto-generated method stub
        
    }

}
