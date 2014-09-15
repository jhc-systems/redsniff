package jhc.redsniff.internal.matchers;

import static jhc.redsniff.core.Describer.describable;
import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;

public class MismatchLastMatcherFilterResult<E> extends MismatchFilterResult<E> {

	public MismatchLastMatcherFilterResult(CollectionOf<E> foundElements, Description mismatchDescription) {
		super(foundElements, mismatchDescription);
	}

	@Override
	public void describeTo(
			Description notFoundDescription) {
		notFoundDescription.appendText(mismatchDescription.toString());
	}
}