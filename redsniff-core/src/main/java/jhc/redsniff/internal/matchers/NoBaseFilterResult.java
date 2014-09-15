package jhc.redsniff.internal.matchers;

import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class NoBaseFilterResult<E> extends FilterResult<E> {

	private SelfDescribing baseWithoutMatchers;
	
	public NoBaseFilterResult(SelfDescribing baseWithoutMatchers, CollectionOf<E> foundElements) {
		super(foundElements);
		this.baseWithoutMatchers = baseWithoutMatchers;
	}
	
	@Override
	public void describeTo( 
			Description notFoundDescription) {
		notFoundDescription
		        .appendText("No ")
		        .appendDescriptionOf(baseWithoutMatchers)
		        .appendText(" found at all");
	}
}