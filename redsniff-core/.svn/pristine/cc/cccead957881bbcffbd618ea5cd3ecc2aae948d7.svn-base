package jhc.redsniff.internal.describedas;

import jhc.redsniff.core.SFinder;
import jhc.redsniff.internal.finders.WrappingSFinder;

import org.hamcrest.Description;

public class SFinderDescribedAs<E, C> extends WrappingSFinder<E, C> {

	private final String customDescriptionText;
	
	public SFinderDescribedAs(String customDescriptionText, SFinder<E, C> wrappedFinder) {
		super(wrappedFinder);
		this.customDescriptionText = customDescriptionText;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(customDescriptionText);
	}
	
	@Override
	public SFinder<E, C> asOptimizedFinder() {
		return new SFinderDescribedAs<E,C>(customDescriptionText, wrappedFinder.asOptimizedFinder()); 
	}
}