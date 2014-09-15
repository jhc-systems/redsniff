package jhc.redsniff.internal.expectations;

import static jhc.redsniff.core.FindingExpectations.expectationOfSome;

import java.util.Collection;

import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Quantity;

/**
 * Specific type of {@link ExpectationChecker} whose find method always returns a collection rather than the defined quantity of the finder 
 * @author InfanteN
 *
 * @param <E>
 * @param <C>
 */
public abstract class AlwaysFindsMultipleElementsChecker<C> extends ExpectationChecker<C> {

    public AlwaysFindsMultipleElementsChecker(C context) {
		super(context);
	}

	@Override
    public  <I, Q extends Quantity<I>> Collection<I> find(Finder<I, Q, C> finder){
        return quantityFoundBy(expectationOfSome(finder)).get();
    }

    @Override
    public abstract <I, Q extends Quantity<I>> CollectionOf<I> quantityFoundBy(FindingExpectation<I, Q, C> expectation);
}
