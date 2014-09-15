package jhc.redsniff.core;

import jhc.redsniff.internal.describedas.LocatorDescribedAs;
import jhc.redsniff.internal.describedas.MFinderDescribedAs;
import jhc.redsniff.internal.describedas.MatcherLocatorDescribedAs;
import jhc.redsniff.internal.describedas.SFinderDescribedAs;
import jhc.redsniff.internal.locators.MatcherLocator;

import org.hamcrest.Matcher;

public final class DescribedAs {

	private DescribedAs(){}
	
	public static <E, C> MatcherLocator<E, C> describedAs(String customDescriptionText,
	        MatcherLocator<E, C> wrappedMatcherLocator) {
	    return new MatcherLocatorDescribedAs<E, C>(customDescriptionText, wrappedMatcherLocator);
	}

	public static <E, C> Locator<E, C> describedAs(String customDescriptionText, Locator<E, C> locator) {
	    return new LocatorDescribedAs<E, C>(customDescriptionText, locator);
	}

	public static <E, C> Matcher<E> describedAs(String customDescriptionText, Matcher<E> matcher) {
	    return org.hamcrest.core.DescribedAs.describedAs(customDescriptionText, matcher);
	}

	public static <E, C> MFinder<E, C> describedAs(String customDescriptionText, MFinder<E,  C> finder){
		return new MFinderDescribedAs<E, C>(customDescriptionText, finder);
	}

	public static <E, C> SFinder<E, C> describedAs(String customDescriptionText, SFinder<E,  C> finder){
		return new SFinderDescribedAs<E, C>(customDescriptionText, finder);
	}

}
