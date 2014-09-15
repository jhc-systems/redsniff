package jhc.redsniff.internal.describedas;

import jhc.redsniff.internal.core.CollectionOf;
import jhc.redsniff.internal.locators.MatcherLocator;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class MatcherLocatorDescribedAs<E, C> extends TypeSafeDiagnosingMatcher<E> implements
        MatcherLocator<E, C> {

    private final MatcherLocator<E, C> wrappedMatcherLocator;
    private String customDescriptionText;

    public MatcherLocatorDescribedAs(String customDescriptionText, MatcherLocator<E, C> wrappedMatcherLocator) {
        this.wrappedMatcherLocator = wrappedMatcherLocator;
        this.customDescriptionText = customDescriptionText;
    }

    @Override
    public void describeTo(Description description) {
        wrappedMatcherLocator.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(E item, Description mismatchDescription) {
        boolean matches = wrappedMatcherLocator.matches(item);
        if (!matches)
            wrappedMatcherLocator.describeMismatch(item, mismatchDescription);
        return matches;
    }

    @Override
    public CollectionOf<E> findElementsFrom(C context) {
        return wrappedMatcherLocator.findElementsFrom(context);
    }

    @Override
    public boolean canBehaveAsLocator() {
        return wrappedMatcherLocator.canBehaveAsLocator();
    }

    @Override
    public String nameOfAttributeUsed() {
        return wrappedMatcherLocator.nameOfAttributeUsed();
    }

    @Override
    public void describeLocatorTo(Description description) {
        description.appendText(customDescriptionText);
    }

	@Override
	public int specifity() {
		return wrappedMatcherLocator.specifity();
	}
}