package jhc.redsniff.internal.finders.within;

import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;

import java.util.LinkedHashMap;
import java.util.Map;

import jhc.redsniff.core.Describer;
import jhc.redsniff.core.FindingExpectations;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.finders.BaseMFinder;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Composite {@link MFinder} class representing a {@link MFinder} within another {@link MFinder}
 * 
 * @see {@link MFinder#withinA(MFinder)
 *
 * @param <E0> the type of element found by the inner finder and which this composite finder will return
 * @param <C0> the type of context the inner finder can search in
 * @param <E1> the type of element found by the outer finder. must be a subtype of C0
 * @param <C1> the type of context searched by the outer finder, and which this composite finder will search in
 */
public class MFinderWithinMFinder<E0, C0, E1 extends C0, C1 > extends	BaseMFinder<E0, C1>{

 
	private final MFinder<E0, C0> innerFinder;
	private final MFinder<E1, C1> scopeFinder;

	public MFinderWithinMFinder(MFinder<E0, C0> innerFinder,
	        MFinder<E1, C1> scopeFinder) {
		this.innerFinder = innerFinder;
		this.scopeFinder = scopeFinder;
	}

	public CollectionOf<E0> findFrom(C1 outerContext,Description notFoundDescription) {
	    Description descriptionToDescribeFindingContextTo;
	    
	    if(scopeFinder instanceof MFinderWithinMFinder)
	        descriptionToDescribeFindingContextTo = notFoundDescription;
	    else
	        descriptionToDescribeFindingContextTo = Describer.newDescription();
	        
		CollectionOf<E1> contexts = scopeFinder.findFrom(outerContext, descriptionToDescribeFindingContextTo);
		if(contexts.isEmpty()) {
		  
		   if(descriptionToDescribeFindingContextTo != notFoundDescription) {
		        describeNotFindingOuterContextTo(notFoundDescription);
		        notFoundDescription.appendText("\nbecause\n");
		       Describer.concat(notFoundDescription, descriptionToDescribeFindingContextTo);
		   }
		   return CollectionOf.empty();
		}
		Map<C0, ExpectationCheckResult<E0, CollectionOf<E0>>> resultsWithinEachContext = findWithinEachContext(contexts);
		 CollectionOf<E0> allFoundElements = collectAllFoundElementsFrom(resultsWithinEachContext);
		if(allFoundElements.isEmpty()){
		    describeNotFindingInnerItemWithinEachOuterContext(resultsWithinEachContext, notFoundDescription);
		    return CollectionOf.empty();
		}
		return allFoundElements;
	}
	
	   private CollectionOf<E0> collectAllFoundElementsFrom(Map<C0, ExpectationCheckResult<E0, CollectionOf<E0>>> resultsWithinEachContext) {
	        CollectionOf<E0> allFoundElements = CollectionOf.fresh();
	        for (ExpectationCheckResult<E0, CollectionOf<E0>> result : resultsWithinEachContext.values()) {
	            allFoundElements.addAll(result.foundQuantity());
	        }
	        return allFoundElements;
	    }

	    private Map<C0, ExpectationCheckResult<E0, CollectionOf<E0>>> findWithinEachContext(CollectionOf<E1> foundContexts) {
	        Map<C0, ExpectationCheckResult<E0, CollectionOf<E0>>> resultsMap = new LinkedHashMap<C0, ExpectationCheckResult<E0, CollectionOf<E0>>>();
	        

	        for (C0 innerContext : foundContexts) {
	            ExpectationCheckResult<E0, CollectionOf<E0>> innerResult = checkerFor(innerContext).resultOfChecking(FindingExpectations.expectationOfSome(innerFinder));
	            resultsMap.put(innerContext, innerResult);
	        }
	        return resultsMap;
	    }

	private void describeNotFindingOuterContextTo(
            Description notFoundDescription) {
        notFoundDescription
                .appendDescriptionOf(scopeFinder)
                .appendText("\nwithin which to search for {")
                .appendDescriptionOf(innerFinder).appendText("}");

    }
	private void describeNotFindingInnerItemWithinEachOuterContext(
            Map<C0,ExpectationCheckResult<E0, CollectionOf<E0>>> resultsWithinEachContext,
            Description notFoundDescription) {
        for (C0 outerElement : resultsWithinEachContext.keySet()) {
            notFoundDescription
                    .appendText("\n within: ")
                    .appendDescriptionOf(Describer.describable(outerElement))
                    .appendText("\n\t");
            ExpectationCheckResult<E0, CollectionOf<E0>> seekResult = resultsWithinEachContext
                    .get(outerElement);
            seekResult.describeTo(notFoundDescription);
            notFoundDescription.appendText("\n");
        }
    }
	@Override
	public void describeTo(Description description) {
		description
			.appendDescriptionOf(innerFinder);
		
		if(scopeFinder instanceof MFinderWithinMFinder) {
			description.appendText(" within:");
			Describer.indent(description)
						.appendText("a ")
					   .appendDescriptionOf(scopeFinder);
			Describer.outdent(description);
		}
		else
			description.appendText(", within a ").appendDescriptionOf(scopeFinder);
	}

	public static <I0, S0, I1 extends S0, S1> MFinderWithinMFinder<I0, S0, I1, S1> finderWithin(
			MFinder<I0,S0> innerFinder, MFinder<I1,S1> scopeFinder) {
		return new MFinderWithinMFinder<I0, S0, I1, S1>(innerFinder, scopeFinder);
	}


    @Override
    public MFinder<E0, C1> that(Matcher<? super E0> elementMatcher) {
        return finderWithin(this.innerFinder.that(elementMatcher), this.scopeFinder);
    }

    
    @Override
    public MFinder<E0, C1> thatOpt(Matcher<? super E0> elementMatcher) {
        return finderWithin(this.innerFinder.that(elementMatcher), this.scopeFinder);
    }

    
    @Override
    public <E2 extends C1, C2> MFinder<E0, C2> withinA(MFinder<E2, C2> outerScopeFinder) {
        return finderWithin(this.innerFinder, this.scopeFinder.withinA(outerScopeFinder));
    }

    @Override
    public <OE extends C1, OC> MFinder<E0, OC> withinThe(SFinder<OE, OC> outerScopeFinder) {
      return MFinderWithinMFinder.finderWithin(this.innerFinder, this.scopeFinder.withinThe(outerScopeFinder));
    }

	@Override
	public MFinder<E0, C1> asOptimizedFinder() {
		return new MFinderWithinMFinder<>(innerFinder.asOptimizedFinder(), scopeFinder.asOptimizedFinder());
	}

	@Override
	public MFinder<E0, C1> optimizedWith(Matcher<? super E0> matcher) {
		return new MFinderWithinMFinder<>(innerFinder.optimizedWith(matcher), scopeFinder);//should scopeFinder be optimized here?
	} 

}
