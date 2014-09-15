package jhc.redsniff.internal.matchers;

import org.hamcrest.Description;

import jhc.redsniff.internal.core.CollectionOf;

public class FoundFilterResult<E> extends FilterResult<E> {

	public FoundFilterResult(CollectionOf<E> foundElements) {
		super(foundElements);
	}

	@Override
	public void describeTo(Description arg0) {
	}
}