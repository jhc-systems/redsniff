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
package jhc.redsniff.internal.expectations;

import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.SelfDescribing;

/**
 * Represents an expectation on a context, about what is found within it. 
 * Typically consists of a {@link jhc.redsniff.core.Finder} and a {@link jhc.redsniff.internal.predicates.Predicate} 
 * on the results found by it.
 * @param <E> the type of element
 * @param <Q> the type of Quantity - either {@link Item} or {@link CollectionOf}
 * @param <C> the type of the context in which we expect to find something
 */
public interface FindingExpectation<E, Q extends Quantity<E>, C>  extends SelfDescribing{
	
	/**
	 * check the expectation for the given context by querying the finder against the context
	 * and checking the predicate is satisfied by the results.
	 * <p> 
	 * Will check at first using optimization on the finder, 
	 * but if not satisfied will run again without optimization, in order to get a message
	 * in terms that the original finder used. 
	 * @param context
	 * @return the result, which indicates whether successful or not if not containing a description of what was wrong
	 */
    ExpectationCheckResult<E, Q> checkFrom(C context);
    
    /**
	 * check the expectation for the given context by querying the finder against the context
	 * and checking the predicate is satisfied by the results.
	 * <p> 
	 * Will only check using optimization on the finder - we just want to know quickly whether the expectation is met or not
	 * (typically used when waiting for something to be true, when we don't want detailed description when it's not)
	 * @param context
	 * @return the result, which indicates whether successful or not if not containing a description of what was wrong
	 */
    ExpectationCheckResult<E, Q> checkFromFailFast(C context);
}