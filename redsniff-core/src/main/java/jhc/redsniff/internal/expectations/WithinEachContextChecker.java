package jhc.redsniff.internal.expectations;

import static jhc.redsniff.core.Describer.asString;
import static jhc.redsniff.core.FindingExpectations.*;

import java.util.Collection;

import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
//C1 is inner context
//E1 is inner element - irrelevant
//C0 is outer context -what we need parentContext to be and what we need the context finder to find from
//E0 is what the context finder finds - it must extend C1
public class WithinEachContextChecker<C1, E0 extends C1, C0> extends AlwaysFindsMultipleElementsChecker<C1>  implements SelfDescribing {

	private MFinder<E0, C0> contextFinder;
	private CollectionOf<C0> parentContexts;
	private SelfDescribing parentContextDescription;
	private String eachString;
	
	public WithinEachContextChecker(CollectionOf<C0> parentContexts,
			MFinder<E0, C0> contextFinder, SelfDescribing parentContextDescription, String eachString) {
		super(null);
		this.parentContexts = parentContexts;
		this.contextFinder = contextFinder;
		this.parentContextDescription = parentContextDescription;
		this.eachString = eachString;
	}
	
	
	@SuppressWarnings("unchecked")
    @Override
	public <I1, Q1 extends Quantity<I1>> CollectionOf<I1> quantityFoundBy(FindingExpectation<I1, Q1, C1> expectation) {
		CollectionOf<I1> allFindings = CollectionOf.fresh();
			for(C1 context:contextsToSearch(expectingToSearchWithinTo(asString(expectation)))){
				Quantity<I1> findings = checkerFor(context)
											.quantityFoundBy(contextPrinting(this,expectation));

				if(findings instanceof CollectionOf)
					allFindings.addAll((Collection<I1>)findings.get());
				else
					allFindings.add((I1)findings.get());
			}
		return allFindings;
	}
	
	
	@Override
	public <I1, Q1 extends Quantity<I1>> boolean isSatisfied(FindingExpectation<I1, Q1, C1> expectation) {
	        boolean isMet = true;
	            for(C1 context : contextsToSearch(expectingToSearchWithinTo(asString(expectation))))
	                isMet &= checkerFor(context).isSatisfied(contextPrinting(this, expectation));
	        return isMet;
	}
	

  @Override
  public <I1, Q1 extends Quantity<I1>> void assertThe(FindingExpectation<I1, Q1, C1> expectation) {
	  for(C1 context : contextsToSearch(expectingToSearchWithinTo(asString(expectation))))
		  checkerFor(context).assertThe(contextPrinting(this,expectation));
  }


	@Override
	public <C2, E1 extends C2> WithinEachContextChecker<C2, E1, C1> inEachContextFoundBy(MFinder<E1, C1> innerContextFinder) {
		CollectionOf<C1> innerContexts = contextsToSearch(expectingToSearchWithinTo(asString(expectationOfSome(innerContextFinder))));
		return new WithinEachContextChecker<C2, E1, C1>(innerContexts, innerContextFinder, this, eachString);
	}

	@Override
	public <C2, E1 extends C2> WithinSingleContextChecker<C2, E1, C1> inSingleContextFoundBy(SFinder<E1,C1> innerContextFinder){
//		SFinder<E1, C1> innerContextFinder) {
//		CollectionOf<C1> innerContexts = contextsToSearch(expectingToSearchWithinTo(asString(expectationOfSome(innerContextFinder))));
//		return new WithinEachContextChecker<C2, E1, C1>(innerContexts, innerContextFinder, this, eachString);
			throw new UnsupportedOperationException("inSingleContext from inEach");
	}


	public CollectionOf<C1> contextsToSearch(
			FindingExpectation<E0, CollectionOf<E0>, C0> expectingToSearchWithinTo) {
		CollectionOf<C1> allContexts=CollectionOf.fresh();
		for(C0 parentContext: parentContexts){
			allContexts.addAll(checkerFor(parentContext).thatWhichIsFoundBy(expectingToSearchWithinTo));
		}
		return allContexts;

	}
	public <I1> FindingExpectation<E0, CollectionOf<E0>, C0> expectingToSearchWithinTo(String purposeOfSearching){
		return expectationWithPurpose(contextFinder, "search within to find " + purposeOfSearching);
	}
	
	@Override
	public void describeTo(Description description) {
		 if (parentContextDescription != null)
           description.appendDescriptionOf(parentContextDescription);
       description.appendText("\n, in ").appendText(eachString + " ").appendDescriptionOf(contextFinder);
		
	}


}
