package jhc.redsniff.internal.matchers;

import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;

public abstract class MismatchFilterResult<E> extends FilterResult<E> {

	protected Description mismatchDescription;

	public MismatchFilterResult(CollectionOf<E> foundElements, Description mismatchDescription) {
		super(foundElements);
		this.mismatchDescription = mismatchDescription;
	}

	 @Override
	public abstract void describeTo(Description notFoundDescription);

	
}