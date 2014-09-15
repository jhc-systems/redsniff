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
package jhc.redsniff.core;

import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 * The core concept used in redsniff is a Finder, and this represents something similar to a WHERE clause in sql.
 * It is an expression of something we want to find on the page, or other particular context, e.g. a button with text 'submit' on a webpage.
 * 
 * Currently has asOptimizedFinder method - looking at ways to make this not part of public API
 *
 * @param <E> the type of the elements the Finder finds
 * @param <Q> the arity (ie Quantity type) of what is found i.e. whether single or multiple results will be returned.
 * @param <C> the type of the context in which the Finder searches
 */
public interface Finder<E, Q  extends Quantity<E>, C> extends SelfDescribing {
	/**
	 * Implement this method to find some Quantity of elements of type E within a context of type C.
	 * If the relevant element is not found, the method should write explanatory reason to the notFoundDescription
	 * @param context the context in which to search
	 * @param notFoundDescription the Description to which to write reason for not finding the expected element(s)
	 * @return
	 */
	Q findFrom(C context, Description notFoundDescription);
	
	/**
	 * Used internally to optimize composite finders. May be removed from this interface shortly.
	 * @return a restructured composite {@link Finder} with the same criteria but optimized.
	 */
	Finder<E, Q, C> asOptimizedFinder();//TODO - could these be hidden from users somehow?
}