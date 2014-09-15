package jhc.redsniff.internal.expectations;

import static jhc.redsniff.core.Describer.asString;
import static jhc.redsniff.core.Describer.newDescription;
import static jhc.redsniff.core.FindingExpectations.contextPrinting;
import static jhc.redsniff.core.FindingExpectations.expectationOfSome;
import static jhc.redsniff.core.FindingExpectations.expectationWithPurpose;
import static jhc.redsniff.util.Util.failWith;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
//C1 is inner context
//E1 is inner element - irrelevant
//C0 is outer context -what we need parentContext to be and what we need the context finder to find from
//E0 is what the context finder finds - it must extend C1
public class WithinSingleContextChecker<C1, E0 extends C1, C0> extends ExpectationChecker<C1>  implements SelfDescribing {

	private SFinder<E0, C0> contextFinder;
	private SelfDescribing parentContextDescription;
	private String eachString;
	private final ExpectationChecker<C0> parentChecker;
	
	
    public WithinSingleContextChecker(ExpectationChecker<C0> parentChecker,
			SFinder<E0, C0> contextFinder, SelfDescribing parentContextDescription, String eachString) {
		super(null);
		this.contextFinder = contextFinder;
		this.parentContextDescription = parentContextDescription;
		this.eachString = eachString;
		this.parentChecker = parentChecker;
	}



	@Override
	public <I1, Q1 extends Quantity<I1>> Quantity<I1> quantityFoundBy(FindingExpectation<I1, Q1, C1> expectation) {
		C1 context = contextToSearch(expectingToSearchWithinTo(asString(expectation)));
		return checkerFor(context).quantityFoundBy(contextPrinting(this,expectation));
		
	}
	
	
	@Override
	public <I1, Q1 extends Quantity<I1>> boolean isSatisfied(FindingExpectation<I1, Q1, C1> expectation) {
	        boolean isMet = true;
	          C1 context = contextToSearch(expectingToSearchWithinTo(asString(expectation)));
	          return checkerFor(context).isSatisfied(contextPrinting(this, expectation));
	}
	

  @Override
  public <I1, Q1 extends Quantity<I1>> void assertThe(FindingExpectation<I1, Q1, C1> expectation) {
	  C1 context = contextToSearch(expectingToSearchWithinTo(asString(expectation)));
	  checkerFor(context).assertThe(contextPrinting(this,expectation));
  }


  
	@Override
	public <C2, E1 extends C2> WithinSingleContextChecker<C2, E1, C1> inSingleContextFoundBy(
		SFinder<E1, C1> innerContextFinder) {
		return new WithinSingleContextChecker<C2, E1, C1>(this, innerContextFinder, this, eachString);
	}


	@Override
	public <C2, E1 extends C2> WithinEachContextChecker<C2, E1, C1> inEachContextFoundBy(MFinder<E1, C1> innerContextFinder) {
		C1 innerContext = contextToSearch(expectingToSearchWithinTo(asString(expectationOfSome(innerContextFinder))));
		return new WithinEachContextChecker<C2, E1, C1>(CollectionOf.collectionOf(innerContext), innerContextFinder, this, "each");
	}
	                             
	                             
                                 

	@Override
	public <E, Q extends Quantity<E>> ExpectationCheckResult<E, Q> resultOfChecking(
			FindingExpectation<E, Q, C1> expectation) {
		 C1 context = contextToSearch(expectingToSearchWithinTo(asString(expectation)));
		 return checkerFor(context).resultOfChecking(expectation);
	}



	public C1 contextToSearch(
			FindingExpectation<E0, Item<E0>, C0> expectingToSearchWithinTo) {
			return parentChecker.thatWhichIsFoundBy(expectingToSearchWithinTo).get();
	}
	
	public <I1> FindingExpectation<E0, Item<E0>, C0> expectingToSearchWithinTo(String purposeOfSearching){
		return expectationWithPurpose(contextFinder, "search within to find " + purposeOfSearching);
	}
	
	@Override
	public void describeTo(Description description) {
		 if (parentContextDescription != null)
           description.appendDescriptionOf(parentContextDescription).appendText("\n,");
		 else
			 description.appendText("\n");
       description.appendText("in " + eachString + " ").appendDescriptionOf(contextFinder);
		
	}



	@Override
	protected <E, Q extends Quantity<E>> void failWithExpectationAndResultMessage(
			FindingExpectation<E, Q, C1> expectation,
			ExpectationCheckResult<E, Q> result) {

		  failWith(newDescription()
	                .appendText("Expected: ")
	                .appendDescriptionOf(this).appendText(":\n")
	                .appendDescriptionOf(expectation)
	                .appendText("\nbut:\n")
	                .appendDescriptionOf(result));
	}

	

}
