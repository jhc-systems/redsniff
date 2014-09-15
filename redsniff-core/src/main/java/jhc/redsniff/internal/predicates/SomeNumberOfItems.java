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



import static jhc.redsniff.core.Describer.describable;

import java.util.Collection;

import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
public class SomeNumberOfItems<E> implements Predicate<E, CollectionOf<E>> {
	
	private Matcher<Integer> cardinalityConstraint;
	
	protected SomeNumberOfItems(Matcher<Integer> cardinalityConstraint) {
		this.cardinalityConstraint = cardinalityConstraint;
	}

	@Override
	public void describePredicateOnFinder(Finder<E, CollectionOf<E>, ?> finder,	Description description) {
		describeNumberExpectedTo(description);		
		description.appendDescriptionOf(finder);
	}

	@Override
	public boolean isSatisfiedBy(CollectionOf<E> foundElements) {
		return cardinalityConstraint.matches(foundElements.size());
	}
	
	@Override
	public void diagnoseNotSatisfyingTo(Description dissatisfactionDescription,
			CollectionOf<E> foundElements, Description notFoundDescription) {
		if(foundElements.size()>0)
			describeActualNumberFound(foundElements, dissatisfactionDescription);
		else{
			dissatisfactionDescription
				.appendText("Not found - ")
				.appendText(notFoundDescription.toString());
		}
	}
	
	protected void describeActualNumberFound(Collection<E> foundElements,
			Description failureDescription) {
		failureDescription.appendText("found ")
			.appendValue(foundElements.size())
			.appendText(" such elements")
			.appendDescriptionOf(describable(foundElements));
	}
	
	protected void describeNumberExpectedTo(Description description) {
		if(cardinalityConstraint instanceof CustomNumberMatcher)
			description
			.appendDescriptionOf(cardinalityConstraint)
			.appendText(" ");
		
		else
			description
			.appendText("there to be ")
			.appendDescriptionOf(cardinalityConstraint)
			.appendText(" of ");
	}
	
	public static  interface CustomNumberMatcher{}
	
	@Factory
	public static <E> Predicate<E, CollectionOf<E>> aNumberOfElements(Matcher<Integer> cardinalityConstraint){
		return new SomeNumberOfItems<E>(cardinalityConstraint);
	}

}
