package jhc.redsniff.internal.finders.within;

import static jhc.redsniff.core.Describer.describable;
import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.finders.BaseMFinder;

import org.hamcrest.Description;


public class SFinderWithinMFinder<E, C, OE extends C, OC> extends BaseMFinder<E, OC> {

    private SFinder<E, C> innerSFinder;
    private MFinder<OE, OC> scopeFinder;

    public SFinderWithinMFinder(SFinder<E, C> baseSFinder, MFinder<OE, OC> scopeFinder) {
        this.innerSFinder = baseSFinder;
        this.scopeFinder = scopeFinder;
    }
    
    public CollectionOf<E> findFrom(OC context, Description notFoundDescription) {
        ExpectationCheckResult<OE, CollectionOf<OE>> resultOfChecking = checkerFor(context).resultOfChecking(expectationOfSome(scopeFinder));
        if(!resultOfChecking.meetsExpectation())
            resultOfChecking.describeTo(notFoundDescription);
        CollectionOf<E> innerElements = CollectionOf.fresh();
        for(OE scopeElement:resultOfChecking.foundQuantity()) {
        	Description notFoundInnerDescription = Describer.newDescription();
            Item<E> innerElement = innerSFinder.findFrom(scopeElement, notFoundInnerDescription);
            if(innerElement.hasAmount())
            	innerElements.add(innerElement.get());
            else {
            	notFoundDescription
            			.appendText("\n within: {")
                		.appendDescriptionOf(describable(scopeElement))
                		.appendText("}\n\t")
                		.appendDescriptionOf(describable(notFoundInnerDescription));
            	 return CollectionOf.empty();
            }
        }
        return innerElements;
    }


    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(innerSFinder).appendText(" within each ").appendDescriptionOf(scopeFinder); 
    }

	@Override
	public MFinder<E, OC> asOptimizedFinder() {
		return new SFinderWithinMFinder<>(innerSFinder.asOptimizedFinder(), scopeFinder.asOptimizedFinder());
	}
}
