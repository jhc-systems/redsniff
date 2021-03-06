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
package jhc.redsniff.internal.describedas;

import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.finders.WrappingMFinder;

import org.hamcrest.Description;

public class MFinderDescribedAs<E, C> extends WrappingMFinder<E,C> {

	private final String customDescriptionText;
	
	public MFinderDescribedAs(String customDescriptionText, MFinder<E, C> wrappedFinder) {
		super(wrappedFinder);
	    this.customDescriptionText = customDescriptionText;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(customDescriptionText);
	}

	@Override
	public MFinder<E, C> asOptimizedFinder() {
		return new MFinderDescribedAs<E,C>(customDescriptionText, wrappedFinder.asOptimizedFinder()); 
	}

}