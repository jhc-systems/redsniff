package jhc.redsniff.pageError;

import static jhc.redsniff.core.FindingExpectations.absenceOf;
import static jhc.redsniff.core.FindingExpectations.presenceOf;
import static jhc.redsniff.core.FindingPredicates.absence;
import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import jhc.redsniff.core.Describer;
import jhc.redsniff.internal.core.Quantity;
import jhc.redsniff.internal.expectations.BaseFindingExpectation;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.expectations.FindingExpectation;

import org.hamcrest.Description;


public class NoPageErrorExpectation<E, Q extends Quantity<E>, C> extends BaseFindingExpectation<E, Q, C>  {

    private PageErrorChecker<E, Q, C> pec;

    @SuppressWarnings("unchecked")
    public NoPageErrorExpectation(final PageErrorChecker<E, Q, C> pec) {
        super(absence(pec.errorOnPageIndicatorFinder().getClass()), pec.errorOnPageIndicatorFinder());
        this.pec = pec;
    }


    @Override
    public ExpectationCheckResult<E, Q> checkFrom(C context) {
        Description dissatisfactionDescription = Describer.newDescription();
        //want fail-fast?
        ExpectationCheckResult<E, Q> resultOfCheckingForNoException = 
                checkerFor(context).resultOfChecking(absenceOf(pec.errorOnPageIndicatorFinder()));
      if(!resultOfCheckingForNoException.meetsExpectation()){
          describeExceptionTraceTo(dissatisfactionDescription, context);
          return ExpectationCheckResult.unsatisfactoryResult(null, dissatisfactionDescription);
      }
      else
          return satisfactoryResult(null);
    }

    private void describeExceptionTraceTo(Description errorDescription, C context) {
        ExpectationCheckResult<E, Q> exceptionTraceResult = checkerFor(context).resultOfChecking(presenceOf(pec.exceptionTraceFinder()));
            errorDescription
                .appendText("Got ")
                .appendDescriptionOf(pec)
                .appendText(":\n");
            
        if(!exceptionTraceResult.meetsExpectation())
            errorDescription
                .appendText("and could not find the exception trace using "+Describer.asString(pec.exceptionTraceFinder()))
                .appendDescriptionOf(exceptionTraceResult)
                .appendText("\nFull Page:\n")
                .appendDescriptionOf(Describer.describable(context));
        
        else
            pec.describeExceptionTraceTo(exceptionTraceResult.foundQuantity(), errorDescription);
    }
    
    public static <E, Q extends Quantity<E>, C> FindingExpectation<E,Q, C> noPageErrorExpected(PageErrorChecker<E, Q, C> pec){
        return new NoPageErrorExpectation<E, Q, C>(pec);
    }



    
    
}
