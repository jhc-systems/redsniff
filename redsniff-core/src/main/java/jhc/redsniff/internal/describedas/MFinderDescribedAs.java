package jhc.redsniff.internal.describedas;

import jhc.redsniff.core.MFinder;
import jhc.redsniff.internal.finders.WrappingMFinder;

import org.hamcrest.Description;

public class MFinderDescribedAs<E, C> extends WrappingMFinder<E,C> {

	private final String customDescriptionText;
	
	public MFinderDescribedAs(String customDescriptionText, MFinder<E, C> wrappedFinder) {
		super(wrappedFinder);
	    this.customDescriptionText = customDescriptionText;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(customDescriptionText);
	}

	@Override
	public MFinder<E, C> asOptimizedFinder() {
		return new MFinderDescribedAs<E,C>(customDescriptionText, wrappedFinder.asOptimizedFinder()); 
	}

}