/*******************************************************************************
 * Copyright 2014 JHC Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
