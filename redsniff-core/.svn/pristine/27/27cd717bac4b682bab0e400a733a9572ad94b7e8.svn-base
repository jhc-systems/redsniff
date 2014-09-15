package jhc.redsniff.internal.matchers;

import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;

public class NoFilterPossibleFilterResult<E> extends FilterResult<E> {

	public NoFilterPossibleFilterResult() {
		super(CollectionOf.<E>empty());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Could not filter");
	}

	@Override
	public boolean isInvalid() {
		return true;
	}

}
