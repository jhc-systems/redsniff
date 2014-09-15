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
package jhc.redsniff.internal.finders;

import jhc.redsniff.core.Locator;

public  class SwappedLocatorFinder<E, C> // extends LocatorFinder<E, C> 
{


	public SwappedLocatorFinder(Locator<E, C> newLocator, LocatorFinder<E, C> originalFinder) {
	//	super(newLocator);
	//	addSwappedMatchers(newLocator, originalFinder.locator, originalFinder.matcherListFilter().matchers());
	}

//	private void addSwappedMatchers(Locator<E, C> newLocator, Locator<E,C> originalLocator, List<Matcher<? super E>> originalMatchers) {
//		add(locatorAsMatcher(originalLocator));
//		for(Matcher<? super E> matcher:originalMatchers)
//			if(matcher!=newLocator)
//			   add(matcher);
//	}
//
//	@SuppressWarnings("unchecked")
//	private Matcher<? super E> locatorAsMatcher(Locator<E, C> locator) {
//		return (Matcher<? super E>) locator;
//	}
//
//	@Override
//	protected boolean rootProviderCouldBeSwapped() {
//		return false;
//	}
//
//
//	@Override
//	public CollectionOf<E> findAndMatchFrom(C context,
//			Description notFoundDescription) {
//		CollectionOf<E> found = findBaseFrom(context);
////		if(found.isEmpty())
////			return originalFinder.findAndMatchFrom(context, notFoundDescription);
////		else
//			return filterResultsForMatchers(found, notFoundDescription);
//	}
//	
//	public static <E, C> SwappedLocatorFinder<E, C> swap(Locator<E, C> newLocator, LocatorFinder<E, C> originalFinder){
//		return new SwappedLocatorFinder<E, C>(newLocator, originalFinder);
//	}
}
