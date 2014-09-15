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