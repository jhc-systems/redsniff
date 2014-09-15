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
package jhc.redsniff.internal.predicates;

import jhc.redsniff.core.Finder;
import jhc.redsniff.core.FindingPredicates;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.core.Item;
import jhc.redsniff.internal.core.Quantity;

import org.hamcrest.Description;
/**
 *  A Predicate on an element or a collection of elements meeting a certain criteria.
 *  E.g.: there being 4 of something; something being absent
 *  Specifies the condition on the elements found.
 *  Able to diagnose why the supplied elements are not satisfactory and what it is expecting. 
 *  Basically equivalent to a hamcrest matcher of Quantity<E> but with different signature.
 *  Commonly used predicates are defined in {@link FindingPredicates}
 *
 * @param <E> the type of element
 * @param <Q> the type of Quantity - either {@link Item} or {@link CollectionOf}
 */
public interface Predicate<E, Q extends Quantity<E>> {
	public boolean isSatisfiedBy(Q foundElements);
	
	public void diagnoseNotSatisfyingTo(Description dissatisfactionDescription,Q foundElements,  Description notFoundDescription);
	
	public void describePredicateOnFinder(Finder<E, Q, ?> finder,	Description description);

}
