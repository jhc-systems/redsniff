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
import jhc.redsniff.core.Describer;
import jhc.redsniff.core.Finder;
import jhc.redsniff.internal.core.Item;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class SingleItemMatching<E> implements Predicate<E, Item<E>> {

	private final Matcher<? super E> matcher;

	public SingleItemMatching(Matcher<? super E> matcher) {
		this.matcher = matcher;
	}

	@Override
	public boolean isSatisfiedBy(Item<E> item) {
		return matcher.matches(item.get());
	}

	@Override
	public void diagnoseNotSatisfyingTo(
			Description dissatisfactionDescription, Item<E> item,
			Description notFoundDescription) {
		if(item.hasAmount())
			matcher.describeMismatch(item.get(), dissatisfactionDescription);
		else
			Describer.concat(dissatisfactionDescription, notFoundDescription);
	}

	@Override
	public void describePredicateOnFinder(Finder<E, Item<E>, ?> finder,
			Description description) {
		description.appendDescriptionOf(finder).appendText(" to match ")
				.appendDescriptionOf(matcher);
	}
	
	public static <E> Predicate<E, Item<E>> singleItemMatches(Matcher<? super E> matcher) {
        return new SingleItemMatching<E>(matcher);
    }
}