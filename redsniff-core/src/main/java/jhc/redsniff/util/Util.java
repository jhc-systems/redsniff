/*
 *  Amendments
 *  ----------
 *  dd mon yyyy  Who    Job No.  Description of change
 *  -----------  -----  -------  ---------------------
 *  27 Mar 2012  DPC             Created    
 *
 *
 */

package jhc.redsniff.util;

import jhc.redsniff.core.Finder;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;

public final class Util {
	
    private Util(){}
	
	public static void failWith(Description failureDescription) {
		throw new java.lang.AssertionError(failureDescription.toString());
	}
	
	//TODO - move to some Finder util class
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public static <I, Q extends Quantity<I>, S> SFinder<I, S> asSFinder(Finder<I, Q, S> finder) {
        return (SFinder<I, S>)(SFinder)finder;
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <I, Q extends Quantity<I>, S> MFinder<I, S> asMFinder(Finder<I, Q, S> finder) {
        return (MFinder<I, S>)(MFinder)finder;
    }
}
