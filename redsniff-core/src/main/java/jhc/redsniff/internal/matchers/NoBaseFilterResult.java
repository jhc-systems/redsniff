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

import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class NoBaseFilterResult<E> extends FilterResult<E> {

	private SelfDescribing baseWithoutMatchers;
	
	public NoBaseFilterResult(SelfDescribing baseWithoutMatchers, CollectionOf<E> foundElements) {
		super(foundElements);
		this.baseWithoutMatchers = baseWithoutMatchers;
	}
	
	@Override
	public void describeTo( 
			Description notFoundDescription) {
		notFoundDescription
		        .appendText("No ")
		        .appendDescriptionOf(baseWithoutMatchers)
		        .appendText(" found at all");
	}
}