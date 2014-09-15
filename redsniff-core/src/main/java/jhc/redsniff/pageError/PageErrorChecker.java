package jhc.redsniff.pageError;

import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;


public interface PageErrorChecker<E, Q extends Quantity<E>, C>  extends SelfDescribing {
    Finder<E, Q, C> exceptionTraceFinder();

    Finder<E,  Q, C> errorOnPageIndicatorFinder();
    
    void describeExceptionTraceTo(Q exceptionElements, Description description);

}
