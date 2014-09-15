package jhc.redsniff.internal.finders;

import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.matchers.FilterResult;
import jhc.redsniff.internal.matchers.MatcherFilter;
import jhc.redsniff.internal.matchers.NotFoundNearestMatchersReducedListFilterResult;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class WrappingMatcherFilterer<E> extends MatcherFilterer<E>{

	public WrappingMatcherFilterer(
			SelfDescribing baseWithoutMatchers,
			MatcherFilter<E> matcherFilter, boolean optimized) {
		super(baseWithoutMatchers, matcherFilter, optimized);
	}

	@Override
	protected FilterResult<E> alreadyEmptyResult(
			Description innerNotFoundDescription, CollectionOf<E> elements) {
		return new NotFoundNearestMatchersReducedListFilterResult<E>(
				baseWithoutMatchers, elements, innerNotFoundDescription);
	}
}