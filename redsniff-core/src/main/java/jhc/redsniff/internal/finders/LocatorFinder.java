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
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.locators.MatcherLocator;
import jhc.redsniff.internal.matchers.NoBaseFilterResult;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class LocatorFinder<E, C> extends  BaseMFinder<E, C> {

	private final Locator<E, C> locator;

	public LocatorFinder(Locator<E, C> locator){
		this.locator = locator;
	}
	
	public Locator<E, C> locator() {
		return locator;
	}
	
	@Override
	public void describeTo(Description description) {
		locator.describeLocatorTo(description);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MFinder<E, C> optimizedWith(Matcher<? super E> matcher){
		if(shouldSwapLocatorWith(matcher) )
			return new MFinderThatHasCondition<>(new LocatorFinder<>((MatcherLocator<E,C>)matcher), (MatcherLocator<E, C>)locator);
		else
			return new MFinderThatHasCondition<>(this, matcher);
	}

	@Override
	public MFinder<E, C> asOptimizedFinder() {
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean shouldSwapLocatorWith(Matcher<? super E> matcher) {
		if(couldSwapLocatorWith(matcher))
			return((MatcherLocator) matcher).specifity() > ((MatcherLocator) locator).specifity();
		return false;
	}

	public boolean couldSwapLocatorWith(Matcher<? super E> matcher) {
		boolean locatorCanBehaveAsMatcher = this.locator instanceof MatcherLocator;
		@SuppressWarnings("rawtypes")
		boolean matcherCanBehaveAsLocator = matcher instanceof MatcherLocator && ((MatcherLocator) matcher).canBehaveAsLocator();
		boolean couldSwap = locatorCanBehaveAsMatcher && matcherCanBehaveAsLocator;
		return couldSwap;
	}
	
	@Override
	public CollectionOf<E> findFrom(C context, Description notFoundDescription) {
		CollectionOf<E> foundElements = locator.findElementsFrom(context);
		if(foundElements.isEmpty()){
			notFoundDescription.appendDescriptionOf(new NoBaseFilterResult<>(this, foundElements));
		}
		return foundElements;
//		if(rootProviderCouldBeSwapped()){
//	        Locator<E, C> replacementLocator = findMoreSpecificLocator();
//	        if(replacementLocator != null)
//	            return findUsingSwappedLocator(context, notFoundDescription, replacementLocator);
//	    }
//	    return findUsingCurrent(context, notFoundDescription);
	}
	//v hacky
//	public boolean wouldHaveUsedSwappedLocator(){
//	    return rootProviderCouldBeSwapped() && (findMoreSpecificLocator()!=null);
//	}

//	
//	private Locator<E, C> findMoreSpecificLocator() {
//		int specificityForCurrentLocatorProvider = specificityFor(this.locator);
//		int bestSpecifity = specificityForCurrentLocatorProvider;
//		Locator<E, C> replacementProvider = null;
//		for(Matcher<? super E> matcher:matcherListFilter().matchers()){
//			if(canBehaveAsLocator(matcher)){
//				Locator<E, C> swapCandidate = matcherAsLocator(matcher);
//				int newSpecificity=-1;//XXX bug! we'll swap something with a specificity of zero...
//				if((newSpecificity = specificityFor(swapCandidate)) > bestSpecifity){
//					bestSpecifity = newSpecificity;
//					replacementProvider = swapCandidate;
//				}
//			}
//		}
//		return replacementProvider;
//	}


//	private CollectionOf<E> findUsingSwappedLocator(C context, Description notFoundDescription, Locator<E, C>replacementProvider) {
//		LocatorFinder<E, C> swappedFinder = swappedFinder(replacementProvider);
//		return swappedFinder.findFrom(context, notFoundDescription);
//	}
//
//	private LocatorFinder<E, C> swappedFinder(final 
//			Locator<E, C> replacementLocator) {
//	
//		return new SwappedLocatorFinder<E, C>(replacementLocator, this);
//	}
//
//	@SuppressWarnings("unchecked")
//	private Locator<E, C> matcherAsLocator(Matcher<? super E> matcher) {
//		return (Locator<E, C>) matcher;
//	}
	

//	private CollectionOf<E> findUsingCurrent(C context, Description notFoundDescription) {
//		return super.findFrom(context, notFoundDescription);
//	}


	@Factory
	public static <E, C> MFinder<E, C> finderForLocator(Locator<E, C> locator){
		return new LocatorFinder<E, C>(locator);
	}

	
}
