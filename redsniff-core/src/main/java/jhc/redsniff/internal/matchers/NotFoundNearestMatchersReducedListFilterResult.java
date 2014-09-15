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
package jhc.redsniff.internal.matchers;

import static jhc.redsniff.core.Describer.describable;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class NotFoundNearestMatchersReducedListFilterResult<E> extends MismatchFilterResult<E> {

	private final SelfDescribing baseWithoutCurrentMatcher;
	public NotFoundNearestMatchersReducedListFilterResult(
			SelfDescribing baseWithoutMatchers,
			CollectionOf<E> foundElements, 
			Description mismatchDescription) {
		super(foundElements, mismatchDescription);
		this.baseWithoutCurrentMatcher = baseWithoutMatchers;
	}

	@Override
	public void describeTo(
			Description notFoundDescription) {
		notFoundDescription
		        .appendText("but searching for ")
		        .appendDescriptionOf(baseWithoutCurrentMatcher)
		        .appendText(mismatchDescription.toString());
	}
}