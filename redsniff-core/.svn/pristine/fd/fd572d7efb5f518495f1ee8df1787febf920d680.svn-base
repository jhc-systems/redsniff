package jhc.redsniff.internal.matchers;

import static jhc.redsniff.core.Describer.describable;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;

public class NotFoundNearestMatchesFilterResult<E> extends MismatchFilterResult<E> {

	public NotFoundNearestMatchesFilterResult(CollectionOf<E> foundElements, Description mismatchDescription) {
		super(foundElements, mismatchDescription);
	}

	@Override
	public void describeTo(
			Description notFoundDescription) {
		notFoundDescription
		        .appendText("\nnearest matches:")
		        .appendText(mismatchDescription.toString());
	}
}