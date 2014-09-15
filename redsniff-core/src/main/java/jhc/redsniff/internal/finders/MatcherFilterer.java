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

import jhc.redsniff.core.Describer;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.expectations.UnoptimizedNotPossibleException;
import jhc.redsniff.internal.matchers.FilterResult;
import jhc.redsniff.internal.matchers.FoundFilterResult;
import jhc.redsniff.internal.matchers.MatcherFilter;
import jhc.redsniff.internal.matchers.MismatchLastMatcherFilterResult;
import jhc.redsniff.internal.matchers.NoFilterPossibleFilterResult;
import jhc.redsniff.internal.matchers.NotFoundNearestMatchesFilterResult;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public abstract class MatcherFilterer<E> implements SelfDescribing{
	
	private static final int PRE_FILTER_NUM_WARN_THRESHOLD = 20;
	private static final double FILTERING_TIME_WARN_THRESHOLD = 2;

	private final MatcherFilter<E> matcherFilter;
	protected final SelfDescribing baseWithoutMatchers;
	private final boolean optimized;
	
	
	public MatcherFilterer(SelfDescribing baseWithoutMatchers, MatcherFilter<E> matcherFilter, boolean optimized) {
		this.baseWithoutMatchers = baseWithoutMatchers;
		this.matcherFilter = matcherFilter;
		this.optimized = optimized;
	}

	public FilterResult<E> filter(Description innerNotFoundDescription,
			CollectionOf<E> elements) {
		
		int initialElementsSizeBeforeCurrentFiltering = elements.size();
		if (initialElementsSizeBeforeCurrentFiltering > PRE_FILTER_NUM_WARN_THRESHOLD){
			if(optimized)
				warn(Describer.asString(this) + " is filtering down from "
						+ elements.size()
						+ " elements - consider a more specific finder or context");
	else
		warn(Describer.asString(this) + " would throw UnoptimizedNotPossibleException");
//				//return new NoFilterPossibleFilterResult<E>();
//				throw new UnoptimizedNotPossibleException();
		}
			
		if (initialElementsSizeBeforeCurrentFiltering == 0)
			return alreadyEmptyResult(innerNotFoundDescription,	elements);
		
		return doFilter(elements, initialElementsSizeBeforeCurrentFiltering);
	}

	public FilterResult<E> doFilter(CollectionOf<E> elements,
			int initialElementsSizeBeforeCurrentFiltering) {
		long startTimeMillis = System.currentTimeMillis();
		Description mismatchDescription = Describer.newDescription();
		CollectionOf<E> filteredByMatcherResults = matcherFilter.filterResults(
				elements, mismatchDescription);
		double timeTakenSecs = (System.currentTimeMillis() - startTimeMillis)/1000d;
		if(timeTakenSecs > FILTERING_TIME_WARN_THRESHOLD)
			warn("took "+timeTakenSecs + " - to filter "+elements.size() + " elements down to "+filteredByMatcherResults.size()+" - " +Describer.asString(this));
		
		if (!filteredByMatcherResults.isEmpty())
			return foundResult(filteredByMatcherResults);
		
		return mismatchResult(initialElementsSizeBeforeCurrentFiltering, mismatchDescription);
	}
	
	
	private void warnIfBigTask(CollectionOf<E> elements,
			int numberBeforeThisFilter) {
		if (numberBeforeThisFilter > PRE_FILTER_NUM_WARN_THRESHOLD)
			warn(Describer.asString(this) + " is filtering down from "
					+ elements.size()
					+ " elements - consider a more specific finder or context");
	}
	
	protected abstract FilterResult<E> alreadyEmptyResult(
			Description innerNotFoundDescription, CollectionOf<E> elements);
	
	private FilterResult<E> mismatchResult(
			int initialElementsSizeBeforeCurrentFiltering,
			Description mismatchDescription) {
		if (initialElementsSizeBeforeCurrentFiltering > 1)
			return new NotFoundNearestMatchesFilterResult<E>(empty(),
					mismatchDescription);
		else
			return new MismatchLastMatcherFilterResult<E>(empty(),
					mismatchDescription);
	}
	
	private CollectionOf<E> empty() {
		return CollectionOf.<E> empty();
	}
	
	private FilterResult<E> foundResult(CollectionOf<E> foundElements) {
		return new FoundFilterResult<E>(foundElements);
	}
	
	private void warn(String message) {
		System.out.println("!!!WARNING!!! " + message);
	}

	@Override
	public void describeTo(Description description) {
		description.appendDescriptionOf(matcherFilter);
	}
}