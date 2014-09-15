package jhc.redsniff.internal.finders;

import jhc.redsniff.core.Describer;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.locators.MatcherLocator;
import jhc.redsniff.internal.matchers.FilterResult;
import jhc.redsniff.internal.matchers.MatcherFilter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

public class MFinderThatHasCondition<E, C> extends BaseMFinder<E, C> {

	private final MFinder<E, C> wrappedFinder;
	protected final MatcherFilterer<E> matcherFilterer;
	private Matcher<? super E> matcher;
	protected boolean optimized = false;

	public MFinderThatHasCondition(MFinder<E, C> wrappedFinder,
			Matcher<? super E> matcher) {
		this.wrappedFinder = wrappedFinder;
		this.matcher = matcher;
		this.matcherFilterer = createMatcherFiltererFor(wrappedFinder, matcher);
	}
	
	protected MFinderThatHasCondition(MFinder<E, C> wrappedFinder,
			Matcher<? super E> matcher, boolean optimized) {
		this.wrappedFinder = wrappedFinder;
		this.matcher = matcher;
		this.optimized = optimized;
		this.matcherFilterer = createMatcherFiltererFor(wrappedFinder, matcher);
	}

	//if both matcherLocators then specificity wins
	//if only one is, then that one wins
	// this wins by default
	//return true only if other > this
	@SuppressWarnings("unchecked")
	private boolean isMoreSpecificThanCurrent(
			Matcher<? super E> other) {
		if (other instanceof MatcherLocator
				&& this.matcher instanceof MatcherLocator) {
			int thisSpecificity = ((MatcherLocator<E,C>) other).specifity();
			int otherSpecificity = ((MatcherLocator<E,C>) this.matcher).specifity();
			return thisSpecificity > otherSpecificity ;
		} else 
			return (other instanceof MatcherLocator);
	}
	
	@Override
	public MFinder<E, C> asOptimizedFinder(){
		return wrappedFinder.asOptimizedFinder().optimizedWith(this.matcher);
	}

	@Override
	public MFinder<E, C> optimizedWith(Matcher<? super E> outerMatcher) {
		if (isMoreSpecificThanCurrent(outerMatcher))
			return ((BaseMFinder<E, C>) wrappedFinder.optimizedWith(outerMatcher)).thatOpt(this.matcher);
		else
			return  ((BaseMFinder<E, C>) wrappedFinder.optimizedWith(this.matcher)).thatOpt(outerMatcher);
	}
	
	@Override
	public CollectionOf<E> findFrom(C context, Description notFoundDescription) {
		Description innerNotFoundDescription = Describer.newDescription();
		CollectionOf<E> elements = 
				wrappedFinder.findFrom(context,	innerNotFoundDescription);
		
		FilterResult<E> filterResult = 
				matcherFilterer.filter(innerNotFoundDescription, elements);
		
		CollectionOf<E> filteredElements = filterResult.foundElements();
		
		if (filteredElements.isEmpty())
			notFoundDescription.appendDescriptionOf(filterResult);
		return filteredElements;
	}

	@Override
	public void describeTo(Description description) {
		wrappedFinder.describeTo(description);
		description
			.appendText(" that ")
			.appendDescriptionOf(matcherFilterer);
	}

	@Override
	public MFinder<E, C> that(Matcher<? super E> elementMatcher) {
		return new MFinderThatHasConditionThatHasCondition<>(this,  elementMatcher, false);
	}
	
	@Override
	protected MFinder<E, C> thatOpt(Matcher<? super E> elementMatcher) {
		return new MFinderThatHasConditionThatHasCondition<>(this,  elementMatcher, true);
	}
	
	protected MatcherFilterer<E> createMatcherFiltererFor(
			SelfDescribing baseWithoutThisMatcher,
			Matcher<? super E> matcher) {
		return new SimpleMatcherFilterer<E>(baseWithoutThisMatcher, new MatcherFilter<E>(matcher), optimized);
	}
	
	protected void describeAsWrappedTo(Description description) {
		wrappedFinder.describeTo(description);
		description
			.appendText(" that: {")
			.appendDescriptionOf(matcherFilterer);
	}
	
	private static class MFinderThatHasConditionThatHasCondition<E,C> extends MFinderThatHasCondition<E,C>{

		private final MFinderThatHasCondition<E, C> rewrappedFinder;

		public MFinderThatHasConditionThatHasCondition(MFinderThatHasCondition<E, C> rewrappedFinder,
				Matcher<? super E> matcher, boolean optimized) {
			super(rewrappedFinder, matcher, true);
			this.rewrappedFinder = rewrappedFinder;
		}

		
		
		@Override
		protected void describeAsWrappedTo(Description description) {
			 rewrappedFinder
					.describeAsWrappedTo(description);
			description
				.appendText(" and ")
				.appendDescriptionOf(matcherFilterer);
		}

		@Override
		public void describeTo(Description description) {
			 rewrappedFinder
					.describeAsWrappedTo(description);
			description
				.appendText(" and ")
				.appendDescriptionOf(matcherFilterer)
				.appendText("}");
		}

		@Override
		protected MatcherFilterer<E> createMatcherFiltererFor(
				SelfDescribing baseWithoutThisMatcher,
				Matcher<? super E> matcher) {
			return new WrappingMatcherFilterer<E>(baseWithoutThisMatcher, new MatcherFilter<E>(matcher), this.optimized);
		}
	}
}